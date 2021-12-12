package com.gelugu.scheduler.domain

import androidx.lifecycle.LiveData

class GetTasksListUseCase(
    private val taskRepository: TaskRepository
) {
    fun getTasksList(): LiveData<List<Task>> {
        return taskRepository.getTasksList()
    }
}