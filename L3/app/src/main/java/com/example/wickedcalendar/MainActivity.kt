package com.example.wickedcalendar

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity(), OnItemListener {

    lateinit var monthYear: TextView
    lateinit var calendarRecycler: RecyclerView
    lateinit var selectedDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()
        selectedDate = LocalDate.now()
        setMonthView()
    }

    private fun setMonthView() {
        monthYear.text = monthYearFromDate(selectedDate)
        val daysInMonth: MutableList<String> = daysInMonthList(selectedDate)

        val calendarAdapter: CalendarAdapter = CalendarAdapter(daysInMonth, this)
        //7 column for 7 days
        val layoutMenager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecycler.layoutManager = layoutMenager
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
            val message = "Selected Date $dayText" + " " + monthYearFromDate(selectedDate)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}