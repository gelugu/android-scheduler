package com.gelugu.scheduler.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.gelugu.scheduler.R
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gelugu.scheduler.domain.Task
import kotlinx.android.synthetic.main.activity_task_item.*

class TaskItemActivity : AppCompatActivity() {
    private lateinit var viewModel: TaskItemViewModel

    private var screenMode = UNKNOWN_MODE
    private var taskItemId = Task.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_item)

        parseIntent()

        viewModel = ViewModelProvider(this)[TaskItemViewModel::class.java]

        addTextChangeListeners()
        addErrorsListeners()

        launchSelectedMode()

        viewModel.isReadyCloseScreen.observe(this) { finish() }
    }

    private fun launchSelectedMode() {
        when (intent.getStringExtra(EXTRA_MODE)) {
            EDIT_MODE -> launchEditMode()
            ADD_MODE -> launchAddMode()
        }
    }

    private fun addErrorsListeners() {
        viewModel.errorInputCount.observe(this) {
            val errorMessage =
                if (it) getString(R.string.invalidEditTextName)
                else null
            til_count.error = errorMessage
        }
        viewModel.errorInputName.observe(this) {
            val errorMessage =
                if (it) getString(R.string.invalidEditTextCount)
                else null
            til_name.error = errorMessage
        }
    }

    private fun addTextChangeListeners() {
        edit_task_name.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Must be overridden, but not useful
            }
            override fun afterTextChanged(s: Editable?) {
                // Must be overridden, but not useful
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }
        })

        edit_task_count.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Must be overridden, but not useful
            }
            override fun afterTextChanged(s: Editable?) {
                // Must be overridden, but not useful
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }
        })
    }

    private fun launchEditMode() {
        viewModel.getTask(taskItemId)
        viewModel.taskItem.observe(this) {
            edit_task_name.setText(it.name)
            edit_task_count.setText(it.date.toString())
        }
        save_button.setOnClickListener {
            val name = edit_task_name.text?.toString()
            val count = edit_task_count.text?.toString()
            viewModel.editTask(name, count)
        }
    }

    private fun launchAddMode() {
        save_button.setOnClickListener {
            val name = edit_task_name.text?.toString()
            val count = edit_task_count.text?.toString()
            viewModel.addTask(name, count)
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) throw RuntimeException("Param screen mode is absent")

        val mode = intent.getStringExtra(EXTRA_MODE)

        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Screen mode is unknown: $mode")
        }

        screenMode = mode

        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_TASK_ID)) throw RuntimeException("Param task id is absent")

            taskItemId = intent.getIntExtra(EXTRA_TASK_ID, -1)
        }
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_TASK_ID = "extra_task_id"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"

        private const val UNKNOWN_MODE = ""

        fun newIntentAddTask(context: Context): Intent {
            val intent = Intent(context, TaskItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, ADD_MODE)
            return intent
        }

        fun newIntentEditTask(context: Context, taskId: Int): Intent {
            val intent = Intent(context, TaskItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, EDIT_MODE)
            intent.putExtra(EXTRA_TASK_ID, taskId)
            return intent
        }
    }
}
