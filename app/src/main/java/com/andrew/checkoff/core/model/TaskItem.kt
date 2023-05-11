package com.andrew.checkoff.core.model

import com.andrew.checkoff.core.database.model.TaskItemEntity

/**
 * External data layer representation of a TaskItem
 */
data class TaskItem(
    val id: Int? = null,
    val title: String,
    val desc: String,
    val completed: Boolean = false,
)

fun TaskItem.asDatabaseModel() = TaskItemEntity(
    id = id ?: -1,
    title = title,
    desc = desc,
    completed = completed,
)