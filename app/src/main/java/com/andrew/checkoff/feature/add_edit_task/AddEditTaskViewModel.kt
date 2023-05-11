package com.andrew.checkoff.feature.add_edit_task

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
class AddEditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _viewState = MutableStateFlow(AddEditTaskState())
    internal val viewState: StateFlow<AddEditTaskState> get() = _viewState
    val taskId = savedStateHandle.get<Int>("taskId")
    val isEditMode = taskId != -1

    init {
        if (taskId != null) {
            viewModelScope.launch {
                taskRepository.getTaskById(taskId).collect { task ->
                    _viewState.update {
                        it.copy(
                            title = task?.title ?: "",
                            desc = task?.desc ?: "",
                        )
                    }
                }
            }
            savedStateHandle.remove<Int>("taskId")
        }
    }

    fun onDonePressed() {
        viewModelScope.launch {
            taskRepository.addTask(
                TaskItem(
                    id = if (isEditMode) taskId else null,
                    title = _viewState.value.title,
                    desc = _viewState.value.desc,
                )
            )
        }
    }

    fun onTitleChanged(title: String) {
        _viewState.update {
            it.copy(
                title = title
            )
        }
    }

    fun onDescChanged(desc: String) {
        _viewState.update {
            it.copy(
                desc = desc
            )
        }
    }
}