package com.example.wickedcalendar

import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarViewHolder(itemView: View, onItemListener: OnItemListener) : RecyclerView.ViewHolder(itemView), OnClickListener  {
    var dayOfMonth: TextView
    private var onItemListener: OnItemListener

    init {
        dayOfMonth = itemView.findViewById(R.id.dayText)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
    }
}