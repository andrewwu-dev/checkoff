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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
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
            taskRepository.getTasks()
                .onStart {
                    _viewState.update { it.copy(isLoading = true) }
                }
                .catch { error ->
                    error.message?.let {
                        UiEvent.ShowSnackbar(
                            message = it
                        )
                    }?.let { sendUiEvent(it) }
                }
                .collect { tasks ->
                    _viewState.update {
                        it.copy(
                            tasks = tasks,
                            isLoading = false,
                        )
                    }
                }
        }
    }

    fun onAddTaskPressed() {
        sendUiEvent(UiEvent.Navigate(Route.ADD_EDIT_TASK))
    }

    fun onCheckBoxPressed(item: TaskItem) {
        viewModelScope.launch {
            taskRepository.updateTask(item.copy(completed = !item.completed))
        }
    }

    fun onTaskPressed(task: TaskItem) {
        sendUiEvent(UiEvent.Navigate(Route.ADD_EDIT_TASK + "?taskId=${task.id}"))
    }

    fun onTaskSwiped(
        task: TaskItem,
        snackbarEvent: UiEvent
    ) {
        viewModelScope.launch {
            _viewState.update { it.copy(deletedTask = task) }
            taskRepository.deleteTask(task)
            sendUiEvent(snackbarEvent)
        }
    }

    fun onUndoDeletePressed() {
        viewModelScope.launch {
            _viewState.value.deletedTask?.let { taskRepository.addTask(it) }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}