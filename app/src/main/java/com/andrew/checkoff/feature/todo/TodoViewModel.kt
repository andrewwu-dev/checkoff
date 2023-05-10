package com.andrew.checkoff.feature.todo

import androidx.lifecycle.ViewModel
import com.andrew.checkoff.core.model.TaskItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor() : ViewModel() {
    private val _viewState = MutableStateFlow(TodoState())
    internal val viewState: StateFlow<TodoState> get() = _viewState

    init {
        _viewState.update {
            it.copy(
                tasks = mutableListOf(
                    TaskItem("title1", "desc1"),
                    TaskItem("title2", "desc2"),
                    TaskItem("title3", "desc3"),
                    TaskItem("title4", "desc4"),
                    TaskItem("title5", "desc5"),
                    TaskItem("title6", "desc6"),
                    TaskItem("title7", "desc7"),
                    TaskItem("title8", "desc8"),
                )
            )
        }
    }

    fun addTask() {
        _viewState.update {
            it.copy(
                tasks = it.tasks.toMutableList().apply {
                    add(TaskItem("title", "desc"))
                }
            )
        }
    }
}