package com.example.wickedcalendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wickedcalendar.databinding.EventCellBinding

class EventAdapter(
    private val events: MutableList<Event>,
    private val clickListener: EventClickListener
): RecyclerView.Adapter<EventViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = EventCellBinding.inflate(from, parent, false)
        return EventViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindEventItem(events[position])
    }
}