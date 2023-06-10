package com.example.taskmaster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.databinding.TaskCellBinding

class TaskAdapter(
    private val events: MutableList<Task>,
    private val clickListener: TaskClickListener
): RecyclerView.Adapter<TaskViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskCellBinding.inflate(from, parent, false)
        return TaskViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindEventItem(events[position])
    }
}