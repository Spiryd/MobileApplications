package com.example.wickedcalendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(days: MutableList<String>, onItemListener: OnItemListener): RecyclerView.Adapter<CalendarViewHolder>() {
    private var days: MutableList<String>
    private var onItemListener: OnItemListener

    init {
        this.days = days
        this.onItemListener = onItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_day, parent, false)
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view, onItemListener)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = days[position]
    }
}