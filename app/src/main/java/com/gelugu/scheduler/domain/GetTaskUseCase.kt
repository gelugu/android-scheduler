package com.gelugu.scheduler.domain

class GetTaskUseCase(
    private val taskRepository: TaskRepository
) {
    fun getTask(taskId: Int): Task {
        return taskRepository.getTask(taskId)
    }
}