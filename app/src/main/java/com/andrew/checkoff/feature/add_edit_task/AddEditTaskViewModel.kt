package com.andrew.checkoff.feature.add_edit_task

import android.content.res.Resources
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrew.checkoff.R
import com.andrew.checkoff.core.data.TaskRepository
import com.andrew.checkoff.core.model.TaskItem
import com.andrew.checkoff.core.nav.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
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
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val taskId = savedStateHandle.get<Int>("taskId")
    val isEditMode = taskId != -1

    init {
        if (taskId != null) {
            viewModelScope.launch {
                taskRepository.getTaskById(taskId)
                    .catch {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = it.message
                                    ?: Resources.getSystem()
                                        .getString(R.string.something_went_wrong)
                            )
                        )
                    }
                    .collect { task ->
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

    fun onDonePressed(snackbarEvent: UiEvent) {
        if (_viewState.value.title.isEmpty()) {
            sendUiEvent(snackbarEvent)
            return
        }
        viewModelScope.launch {
            taskRepository.addTask(
                TaskItem(
                    id = if (isEditMode) taskId else null,
                    title = _viewState.value.title,
                    desc = _viewState.value.desc,
                )
            )
        }
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun onBackPress() {
        sendUiEvent(UiEvent.PopBackStack)
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

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}