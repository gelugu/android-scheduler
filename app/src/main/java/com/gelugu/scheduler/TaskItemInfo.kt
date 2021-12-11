package com.gelugu.scheduler

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.gelugu.scheduler.task.Task
import kotlinx.android.synthetic.main.activity_add_task_item.*
import kotlinx.android.synthetic.main.activity_task_item_info.*
import kotlinx.android.synthetic.main.activity_task_item_info.datePicker
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.util.*

class TaskItemInfo : AppCompatActivity() {
    private fun getTask(filename: String): Task {
        val fileInputStream: FileInputStream? = openFileInput(filename)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String?
        while (run {
                text = bufferedReader.readLine()
                text
            } != null) {
            stringBuilder.append(text)
        }

        return Task(filename, stringBuilder.toString().toLong())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_item_info)

        val taskName = this.intent.getStringExtra("taskName") ?: return
        val task = getTask(taskName)

        val initDate = Calendar.getInstance()
        initDate.time = Date(task.date)

        datePicker.init(
            initDate.get(Calendar.YEAR),
            initDate.get(Calendar.MONTH),
            initDate.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            val newDate = Calendar.getInstance()
            newDate.time = Date(year, month, day)
            task.date = newDate.timeInMillis
        }
        datePicker.minDate = initDate.timeInMillis

        saveTask.setOnClickListener {
            Toast.makeText(applicationContext, "ToDo", Toast.LENGTH_LONG).show()
        }

        deleteTask.setOnClickListener {
            val savedText = "Task \"$taskName\" deleted"
            val notSavedText = "Error while deleting \"$taskName\""

            try {
                deleteFile(taskName)
                Toast.makeText(applicationContext, savedText, Toast.LENGTH_LONG).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, notSavedText, Toast.LENGTH_LONG).show()
            }
        }
    }
}