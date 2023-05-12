package com.andrew.checkoff.core.model

import com.andrew.checkoff.core.database.model.TaskItemEntity
import com.andrew.checkoff.core.network.TaskItemResponse

/**
 * External data layer representation of a TaskItem
 */
data class TaskItem(
    val id: Int? = null,
    val title: String,
    val desc: String,
    val completed: Boolean = false,
)

fun TaskItem.asNetworkModel() = TaskItemResponse(
    id = id ?: 1,
    title = title,
    completed = completed,
)

fun TaskItem.asDatabaseModel() = if (id == null) {
    TaskItemEntity(
        title = title,
        desc = desc,
        completed = completed,
    )
} else {
    TaskItemEntity(
        id = id,
        title = title,
        desc = desc,
        completed = completed,
    )
}