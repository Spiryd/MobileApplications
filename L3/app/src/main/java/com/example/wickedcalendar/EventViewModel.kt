package com.example.wickedcalendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.util.*

class EventViewModel: ViewModel() {
    var events = MutableLiveData<MutableList<Event>>()

    init {
        events.value = mutableListOf()
    }

    fun addEvent(newEvent: Event)
    {
        val list = events.value
        list!!.add(newEvent)
        events.postValue(list)
    }

    fun updateEvent(id: Int, title: String, desc: String, date: LocalDate)
    {
        val list = events.value
        val event = list!!.find { it.id == id }!!
        event.title = title
        event.description = desc
        event.dateString = Event.dateFormatter.format(date)
        events.postValue(list)
    }

    fun deleteEvent(id: Int){
        val list = events.value
        val event = list!!.find { it.id == id }!!
        list.remove(event)
        events.postValue(list)
    }
}