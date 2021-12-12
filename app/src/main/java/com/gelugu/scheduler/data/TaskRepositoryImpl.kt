package com.gelugu.scheduler.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gelugu.scheduler.domain.Task
import com.gelugu.scheduler.domain.TaskRepository
import java.lang.RuntimeException
import kotlin.random.Random

object TaskRepositoryImpl : TaskRepository {

    private val taskListLiveData = MutableLiveData<List<Task>>()
    private val taskList = sortedSetOf<Task>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 1500) {
            val task = Task("task $i", Random.nextLong(), Random.nextBoolean())
            addTask(task)
        }
    }

    override fun addTask(task: Task) {
        if (task.id == Task.UNDEFINED_ID) {
            task.id = autoIncrementId++
        }
        taskList.add(task)

        updateList()
    }

    override fun removeTask(task: Task) {
        removeTask(task.id)
    }

    override fun removeTask(taskId: Int) {
        val task = getTask(taskId)
        taskList.remove(task)

        updateList()
    }

    override fun editTask(task: Task) {
        removeTask(task)
        addTask(task)
    }

    override fun getTask(taskId: Int): Task {
        return taskList.find {
            it.id == taskId
        } ?: throw RuntimeException("Task with id $taskId not found")
    }

    override fun getTasksList(): LiveData<List<Task>> {
        return taskListLiveData
    }

    private fun updateList() {
        taskListLiveData.value = taskList.toList()
    }
}