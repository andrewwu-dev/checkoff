package com.andrew.checkoff.feature.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrew.checkoff.core.data.TaskRepository
import com.andrew.checkoff.core.model.TaskItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {
    private val _viewState = MutableStateFlow(AddTaskState())
    internal val viewState: StateFlow<AddTaskState> get() = _viewState

    fun onDonePressed() {
        viewModelScope.launch {
            taskRepository.addTask(
                TaskItem(
                    title = _viewState.value.title,
                    desc = _viewState.value.desc,
                )
            )
        }
    }
}