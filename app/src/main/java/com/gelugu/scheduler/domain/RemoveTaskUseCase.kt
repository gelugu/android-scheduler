package com.gelugu.scheduler.domain

class RemoveTaskUseCase(
    private val taskRepository: TaskRepository
) {
    fun removeTask(task: Task) {
        taskRepository.removeTask(task)
    }

    fun removeTask(taskId: Int) {
        taskRepository.removeTask(taskId)
    }
}