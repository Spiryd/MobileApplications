package com.example.taskmaster

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.databinding.TaskCellBinding

class TaskViewHolder(
    private val context: Context,
    private val binding: TaskCellBinding,
    private val clickListener: TaskClickListener
): RecyclerView.ViewHolder(binding.root) {
    fun bindEventItem(task: Task){
        binding.description.text = task.description
        binding.deleteButton.setOnClickListener {
            clickListener.deleteTask(task)
        }
    }
}