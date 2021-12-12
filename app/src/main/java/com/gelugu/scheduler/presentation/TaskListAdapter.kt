package com.gelugu.scheduler.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.gelugu.scheduler.R
import com.gelugu.scheduler.domain.Task

class TaskListAdapter : ListAdapter<Task, TaskViewHolder>(TaskDiffCallback()) {

    var onTaskLongClickListener: ((Task) -> Unit)? = null
    var onTaskClickListener: ((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ACTIVE -> R.layout.task_item_active
            VIEW_TYPE_DISABLED -> R.layout.task_item_disabled
            else -> throw RuntimeException("Unknown ViewType[$viewType] (TaskListAdapter)")
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return TaskViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val task = getItem(position)
        return if (task.status) {
            VIEW_TYPE_ACTIVE
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)

        holder.taskName.text = task.name
        holder.taskDate.text = task.date.toString()

        holder.view.setOnLongClickListener {
            onTaskLongClickListener?.invoke(task)
            true
        }

        holder.view.setOnClickListener {
            onTaskClickListener?.invoke(task)
        }
    }

    companion object {
        const val VIEW_TYPE_ACTIVE = 0
        const val VIEW_TYPE_DISABLED = 1

        const val MAX_POOL_SIZE = 15
    }
}