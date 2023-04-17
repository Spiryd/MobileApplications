package com.example.wickedcalendar

interface EventClickListener {
    fun editEvent(event: Event)

    fun deleteEvent(event: Event)
}