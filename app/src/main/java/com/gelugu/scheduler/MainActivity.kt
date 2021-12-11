package com.gelugu.scheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.gelugu.scheduler.task.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private fun getLocalTasks(): List<String> {
        val fileList = this.fileList()

        return MutableList(fileList.size) { fileList[it] }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoListAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_expandable_list_item_1,
            getLocalTasks()
        )

        list.adapter = todoListAdapter

        list.setOnItemClickListener { _, view, _, _ ->
            val itemIntent = Intent(this, TaskItemInfo::class.java)
            itemIntent.putExtra("taskName", (view as TextView).text)
            startActivity(itemIntent)
        }

        addItem.setOnClickListener {
            val addItemIntent = Intent(this, AddTaskItem::class.java)
            startActivity(addItemIntent)
        }
    }

    override fun onResume() {
        super.onResume()

        val todoListAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_expandable_list_item_1,
            getLocalTasks()
        )

        list.adapter = todoListAdapter
    }
}