package com.example.taskmaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.taskmaster.databinding.ActivityAddTaskBinding
import com.example.taskmaster.databinding.ActivityMainBinding
import taskdb.TaskEntityQueries

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private val driver: SqlDriver = AndroidSqliteDriver(TaskDatabase.Schema, this, "test.db")
    private val database = TaskDatabase(driver)
    private val taskEntityQueries: TaskEntityQueries = database.taskEntityQueries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveButton.setOnClickListener {
            taskEntityQueries.insert(binding.dsc.text.toString())
            finish()
        }
    }
}