package com.gelugu.scheduler.domain

import androidx.lifecycle.LiveData

interface TaskRepository {
    fun addTask(task: Task)

    fun removeTask(task: Task)
    fun removeTask(taskId: Int)

    fun editTask(task: Task)

    fun getTask(taskId: Int): Task

    fun getTasksList(): LiveData<List<Task>>
}