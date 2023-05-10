package com.andrew.checkoff.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrew.checkoff.core.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {
    private val _viewState = MutableStateFlow(TodoState())
    internal val viewState: StateFlow<TodoState> get() = _viewState

    init {
        println("TEST")
        viewModelScope.launch {
            taskRepository.getTasks().collect { tasks ->
                _viewState.update {
                    it.copy(
                        tasks = tasks
                    )
                }
            }
        }
    }
}