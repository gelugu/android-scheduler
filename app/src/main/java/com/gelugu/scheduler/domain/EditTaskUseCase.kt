package com.gelugu.scheduler.domain

class EditTaskUseCase(
    private val taskRepository: TaskRepository
) {
    fun editTask(task: Task) {
        taskRepository.editTask(task)
    }
}
