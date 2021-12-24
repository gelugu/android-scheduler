package com.gelugu.scheduler.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gelugu.scheduler.data.TaskRepositoryImpl
import com.gelugu.scheduler.domain.AddTaskUseCase
import com.gelugu.scheduler.domain.EditTaskUseCase
import com.gelugu.scheduler.domain.GetTaskUseCase
import com.gelugu.scheduler.domain.Task
import java.lang.Exception

class TaskItemViewModel: ViewModel() {
    private val repository = TaskRepositoryImpl

    private val getTaskUseCase = GetTaskUseCase(repository)
    private val addTaskUseCase = AddTaskUseCase(repository)
    private val editTaskUseCase = EditTaskUseCase(repository)

    private val _isReadyCloseScreen = MutableLiveData<Unit>()
    val isReadyCloseScreen: LiveData<Unit>
        get() = _isReadyCloseScreen

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _taskItem = MutableLiveData<Task>()
    val taskItem: LiveData<Task>
        get() = _taskItem

    fun getTask(taskId: Int) {
        val task = getTaskUseCase.getTask(taskId)
        _taskItem.value = task
    }

    fun addTask(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInput(name, count)) {
            val task = Task(name, count.toLong(), true)

            finishWork()
            return addTaskUseCase.addTask(task)
        }
    }

    fun editTask(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInput(name, count)) {
            _taskItem.value?.let {
                val item = it.copy(name = name, date = count.toLong())
                editTaskUseCase.editTask(item)
                finishWork()
            }
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            Log.d("INPUT_TASK_COUNT", e.toString())
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true

        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }
    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _isReadyCloseScreen.value = Unit
    }
}