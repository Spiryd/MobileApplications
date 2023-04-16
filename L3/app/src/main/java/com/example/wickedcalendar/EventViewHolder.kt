package com.example.wickedcalendar

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.wickedcalendar.databinding.EventCellBinding

class EventViewHolder(
    private val context: Context,
    private val binding: EventCellBinding,
    private val clickListener: EventClickListener
): RecyclerView.ViewHolder(binding.root) {
    fun bindEventItem(event: Event){
        binding.title.text = event.title
        binding.eventCellContainer.setOnClickListener {
            clickListener.editEvent(event)
        }
    }
}