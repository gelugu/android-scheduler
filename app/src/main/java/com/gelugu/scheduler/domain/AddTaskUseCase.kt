package com.gelugu.scheduler.domain

class AddTaskUseCase(
    private val taskRepository: TaskRepository
) {
    fun addTask(task: Task) {
        taskRepository.addTask(task)
    }
}