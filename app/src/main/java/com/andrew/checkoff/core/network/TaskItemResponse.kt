package com.andrew.checkoff.core.network

import com.andrew.checkoff.core.model.TaskItem

data class TaskItemResponse(
    var id: Int = 0,
    var title: String = "",
    var completed: Boolean = false,
)

fun TaskItemResponse.asDomainModel() = TaskItem(
    id = id,
    title = title,
    desc = "",
    completed = completed,
)