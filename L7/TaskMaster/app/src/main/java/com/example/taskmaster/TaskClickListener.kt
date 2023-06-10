package com.example.taskmaster

interface TaskClickListener {
    fun deleteTask(task: Task)
    fun updateTasks()
}