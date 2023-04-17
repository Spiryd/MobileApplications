package com.example.wickedcalendar

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.wickedcalendar.databinding.EventCellBinding
import java.time.format.DateTimeFormatter

class EventViewHolder(
    private val context: Context,
    private val binding: EventCellBinding,
    private val clickListener: EventClickListener
): RecyclerView.ViewHolder(binding.root) {

    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    fun bindEventItem(event: Event){
        binding.title.text = event.title
        val timeFrame = event.timeFrame()
        if (timeFrame != null) {
            val timeFrameString = timeFormat.format(timeFrame.first) + " - " + timeFormat.format(timeFrame.second)
            binding.timeFrame.text = timeFrameString
        } else{
            binding.timeFrame.text = ""
        }
        binding.eventCellContainer.setOnClickListener {
            clickListener.editEvent(event)
        }
        binding.deleteButton.setOnClickListener {
            clickListener.deleteEvent(event)
        }
    }
}