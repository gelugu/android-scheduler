package com.gelugu.scheduler.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gelugu.scheduler.R

class TaskViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val taskName: TextView = view.findViewById(R.id.taskName)
    val taskDate: TextView = view.findViewById(R.id.taskDate)
}
