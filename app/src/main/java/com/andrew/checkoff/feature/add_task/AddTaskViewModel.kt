package com.andrew.checkoff.feature.add_task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrew.checkoff.core.data.TaskRepository
import com.andrew.checkoff.core.model.TaskItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _viewState = MutableStateFlow(AddTaskState())
    internal val viewState: StateFlow<AddTaskState> get() = _viewState
    val taskId = savedStateHandle.get<Int>("taskId")

    init {
        if (taskId != null) {
            viewModelScope.launch {
                taskRepository.getTaskById(taskId)?.let { taskItem ->
                    _viewState.update {
                        it.copy(
                            title = "taskItem.title",
                            desc = "taskItem.desc",
                        )
                    }
                }
            }
            savedStateHandle.remove<Int>("taskId")
        }
    }

    fun onDonePressed(title: String, desc: String) {
        viewModelScope.launch {
            taskRepository.addTask(
                TaskItem(
                    id = taskId,
                    title = title,
                    desc = desc,
                )
            )
        }
    }
}