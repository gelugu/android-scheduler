package com.gelugu.scheduler.task

import android.content.Context
import android.util.Log
import kotlinx.android.synthetic.main.activity_add_task_item.*
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

class Task(
    var name: String,
    var date: Long
) {

    fun write(context: Context) {
        val data = date
        val fileOutputStream: FileOutputStream

        try {
            fileOutputStream = context.openFileOutput(this.name, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toString().toByteArray())
        } catch (e: Exception){
            Log.e(this.javaClass.name, e.toString())
            e.printStackTrace()
        }
    }
}