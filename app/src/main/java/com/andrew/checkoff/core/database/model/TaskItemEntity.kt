package com.andrew.checkoff.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andrew.checkoff.core.model.TaskItem

@Entity(
    tableName = "task",
)
data class TaskItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val desc: String,
    val completed: Boolean = false,
)

fun TaskItemEntity.asExternalModel() = TaskItem(
    id = id,
    title = title,
    desc = desc,
    completed = completed,
)