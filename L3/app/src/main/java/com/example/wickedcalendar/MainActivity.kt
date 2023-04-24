package com.example.wickedcalendar

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Calendar

class MainActivity : AppCompatActivity(), OnItemListener {
    private lateinit var monthYear: TextView
    private lateinit var calendarRecycler: RecyclerView
    private lateinit var selectedDate: LocalDate
    private var events: HashMap<LocalDate, MutableList<Event>> = hashMapOf()
    private val dataBase by lazy { AppDatabase.getDatabase(this).eventDao() }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val e = intent.getParcelableArrayListExtra("events_out", Event::class.java)
                val date = intent.getStringExtra("date_string")
                if (e != null && date != null){
                    if (e.isNotEmpty()) {
                        events.remove(LocalDate.parse(date))
                        events[LocalDate.parse(date)] = e.toMutableList()
                    } else {
                        events.remove(LocalDate.parse(date))
                    }
                    lifecycleScope.launch {
                        dataBase.deleteAllOnDate(date)
                        dataBase.insertAll(*e.toTypedArray())
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            dataBase.allEvents().collect{eventList ->
                events = hashMapOf()
                Log.i("populateEvents", eventList.first().dateString)
                if(eventList.isNotEmpty()){
                    for (e in eventList){
                        if (events[e.date()] == null){
                            events[e.date()] = mutableListOf(e)
                        }else{
                            events[e.date()]!!.add(e)
                        }
                    }
                }
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()
        selectedDate = LocalDate.now()
        setMonthView()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("year", selectedDate.year)
        outState.putInt("month", selectedDate.monthValue)
        outState.putInt("day", selectedDate.dayOfMonth)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val year = savedInstanceState.getInt("year")
        val month = savedInstanceState.getInt("month")
        val day = savedInstanceState.getInt("day")
        Log.i("onRestoreInstanceState", "$year, $month, $day")
        selectedDate = LocalDate.of(year, month, day)
        setMonthView()
    }

    private fun setMonthView() {
        monthYear.text = monthYearFromDate(selectedDate)
        val daysInMonth: MutableList<String> = daysInMonthList(selectedDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        //7 column for 7 days
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecycler.layoutManager = layoutManager
        calendarRecycler.adapter = calendarAdapter
    }

    private fun daysInMonthList(date: LocalDate): MutableList<String> {
        val days: MutableList<String> = mutableListOf()
        val yearMonth: YearMonth = YearMonth.from(date)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                days.add("")
            } else {
                days.add((i - dayOfWeek).toString())
            }
        }
        return days
    }

    private fun monthYearFromDate(date: LocalDate): String{
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    private fun initWidgets() {
        calendarRecycler = findViewById(R.id.calendarRecyclerView)
        monthYear = findViewById(R.id.monthYearDisplay)
    }

    fun prevMonth(view: View) {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    fun nextMonth(view: View) {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }

    override fun onItemClick(position: Int, dayText: String) {
        if (dayText != ""){
            val intent = Intent(this, OnDay::class.java)
            intent.putExtra("year", selectedDate.year)
            intent.putExtra("month", selectedDate.monthValue)
            intent.putExtra("day", dayText.toInt())
            val e: MutableList<Event> = events[LocalDate.of(selectedDate.year, selectedDate.monthValue, dayText.toInt())] ?: mutableListOf()
            intent.putParcelableArrayListExtra("events", ArrayList(e))
            startForResult.launch(intent)
        }
    }

}