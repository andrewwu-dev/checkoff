package com.andrew.checkoff.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrew.checkoff.core.data.TaskRepository
import com.andrew.checkoff.core.model.TaskItem
import com.andrew.checkoff.core.nav.Route
import com.andrew.checkoff.core.nav.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {
    private val _viewState = MutableStateFlow(TodoState())
    internal val viewState: StateFlow<TodoState> get() = _viewState
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
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

    fun onAddTaskPressed() {
        sendUiEvent(UiEvent.Navigate(Route.ADD_TASK))
    }

    fun onCheckBoxPressed(item: TaskItem) {
        viewModelScope.launch {
            taskRepository.updateTask(item.copy(completed = !item.completed))
        }
    }

    fun onTaskPressed(task: TaskItem) {
        sendUiEvent(UiEvent.Navigate(Route.ADD_TASK + "?taskId=${task.id}"))
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}