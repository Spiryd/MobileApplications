package com.example.taskmaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.taskmaster.databinding.ActivityMainBinding
import taskdb.TaskEntity
import taskdb.TaskEntityQueries

class MainActivity : AppCompatActivity(), TaskClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    private var tasks: MutableList<Task> = mutableListOf()
    private val driver: SqlDriver = AndroidSqliteDriver(TaskDatabase.Schema, this, "test.db")
    private val database = TaskDatabase(driver)
    private val taskEntityQueries: TaskEntityQueries = database.taskEntityQueries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        binding.newEventButton.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
            this.startActivity(intent)
        }
        taskEntityQueries.selectAll().executeAsList().forEach {
            taskEntity -> tasks.add(fromEntity(taskEntity))
        }
        setRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        updateTasks()
    }

    private fun setRecyclerView(){
        val activity = this
        taskViewModel.tasks.postValue(tasks)
        taskViewModel.tasks.observe(this){
            binding.eventRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskAdapter(it, activity)
            }
        }
    }

    override fun deleteTask(task: Task) {
        taskEntityQueries.deleteNoteById(task.id)
        val taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        taskViewModel.deleteTask(task.id)
    }

    override fun updateTasks() {
        tasks.clear()
        taskEntityQueries.selectAll().executeAsList().forEach {
                taskEntity -> tasks.add(fromEntity(taskEntity))
        }
        val taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        taskViewModel.updateTasks(tasks)
    }

    private fun fromEntity(taskEntity: TaskEntity): Task{
        return Task(taskEntity.id, taskEntity.description)
    }
}