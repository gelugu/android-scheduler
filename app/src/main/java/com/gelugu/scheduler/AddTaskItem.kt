package com.gelugu.scheduler

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.gelugu.scheduler.task.Task
import kotlinx.android.synthetic.main.activity_add_task_item.*
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddTaskItem : AppCompatActivity() {
    private val newTaskDaysGap = 1

    private var today = Calendar.getInstance()
    var date = today.timeInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task_item)

        datePicker.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH) + newTaskDaysGap
        ) { _, year, month, day ->
            val newDate = Calendar.getInstance()

            newDate.time = Date(year, month, day)

            date = newDate.timeInMillis
        }
        datePicker.minDate = today.timeInMillis

        saveButton.setOnClickListener {
            val taskName = editTaskName.text.toString()
            val task = Task(taskName, date)

            try {
                writeTask(task)
                finish()
            } catch (e: Exception) {}
        }
    }

    private fun writeTask(task: Task) {
        val savedText = "Task \"${task.name}\" saved"
        val notSavedText = "Error while saving \"${task.name}\""

        try {
            task.write(this)
            Toast.makeText(applicationContext, savedText, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, notSavedText, Toast.LENGTH_LONG).show()
        }
    }
}