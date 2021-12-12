package com.gelugu.scheduler.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gelugu.scheduler.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var taskListAdapter: TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecycleView()
    }

    private fun setupRecycleView() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.taskList.observe(this) {
            taskListAdapter.submitList(it)
        }

        taskListAdapter = TaskListAdapter()
        with(rvTaskList) {
            adapter = taskListAdapter

            recycledViewPool.setMaxRecycledViews(
                TaskListAdapter.VIEW_TYPE_ACTIVE,
                TaskListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                TaskListAdapter.VIEW_TYPE_DISABLED,
                TaskListAdapter.MAX_POOL_SIZE
            )
        }

        setupListeners()
    }

    private fun setupListeners() {
        setupOnClickListener()

        setupOnLongClickListener()

        setupSwipeListener()
    }

    private fun setupOnLongClickListener() {
        taskListAdapter.onTaskLongClickListener = {
            viewModel.toggleTaskStatus(it)
        }
    }

    private fun setupOnClickListener() {
        taskListAdapter.onTaskClickListener = {
            Log.d("CLICK_ON_TASK", it.toString())
        }
    }

    private fun setupSwipeListener() {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = taskListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeTask(task)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(rvTaskList)
    }
}