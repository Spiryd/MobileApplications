package com.example.taskmaster

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel: ViewModel() {
    var tasks = MutableLiveData<MutableList<Task>>()

    init {
        tasks.value = mutableListOf()
    }

    fun addEvent(newTask: Task)
    {
        val list = tasks.value
        list!!.add(newTask)
        tasks.postValue(list)
    }

    fun deleteTask(id: Long){
        val list = tasks.value
        val event = list!!.find { it.id == id }!!
        list.remove(event)
        tasks.postValue(list)
    }

    fun updateTasks(newTasks: MutableList<Task>){
        tasks.postValue(newTasks)
    }
}