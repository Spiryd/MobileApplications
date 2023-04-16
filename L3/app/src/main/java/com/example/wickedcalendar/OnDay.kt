package com.example.wickedcalendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wickedcalendar.databinding.ActivityOnDayBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OnDay : AppCompatActivity(), EventClickListener {
    private lateinit var dayMonthYear: TextView
    private lateinit var date: LocalDate
    private lateinit var events: MutableList<Event>
    private lateinit var binding: ActivityOnDayBinding
    private lateinit var eventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnDayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]
        binding.newEventButton.setOnClickListener{
            EventSheet(null, date).show(supportFragmentManager, "NewEvent")
        }
        dayMonthYear = findViewById(R.id.dayMonthYearDisplay)
        date = LocalDate.of(intent.getIntExtra("year", 1945), intent.getIntExtra("month", 11), intent.getIntExtra("day", 20))
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        dayMonthYear.text = formatter.format(date)
        val e: ArrayList<Event> = intent.getParcelableArrayListExtra("events", Event::class.java)!!
        events = e.toMutableList()
        //events.add(Event("a", date, "a"))
        //Log.i("onCreateOnDay", "$e")
        setRecyclerView()
    }

    private fun setRecyclerView(){
        val activity = this
        eventViewModel.events.value = events
        eventViewModel.events.observe(this){
            binding.eventRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = EventAdapter(it, activity)
            }
        }
    }

    fun goBack(view: View) {
        val data = Intent()
        data.putParcelableArrayListExtra("events_out", ArrayList(events))
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun editEvent(event: Event) {
        EventSheet(event, date).show(supportFragmentManager, "NewEvent")
    }
}