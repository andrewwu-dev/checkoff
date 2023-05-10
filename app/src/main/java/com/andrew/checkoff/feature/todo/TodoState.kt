package com.andrew.checkoff.feature.todo

import com.andrew.checkoff.core.model.TaskItem

internal data class TodoState(
    var tasks: MutableList<TaskItem> = mutableListOf()
)