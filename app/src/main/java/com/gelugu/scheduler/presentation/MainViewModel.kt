package com.gelugu.scheduler.presentation

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gelugu.scheduler.data.TaskRepositoryImpl
import com.gelugu.scheduler.domain.*

class MainViewModel: ViewModel() {
    private val repository = TaskRepositoryImpl

    private val getTasksListUseCase = GetTasksListUseCase(repository)
    private val removeTaskUseCase = RemoveTaskUseCase(repository)
    private val editTaskUseCase = EditTaskUseCase(repository)

    val taskList = getTasksListUseCase.getTasksList()

    fun removeTask(taskId: Int) {
        removeTaskUseCase.removeTask(taskId)
    }
    fun removeTask(task: Task) {
        removeTask(task.id)
    }

    fun toggleTaskStatus(task: Task) {
        val newTask = task.copy(status = !task.status)
        editTaskUseCase.editTask(newTask)
    }
}