package com.andrew.checkoff.core.data

import com.andrew.checkoff.core.model.TaskItem
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: TaskItem)

    suspend fun updateTask(task: TaskItem)

    suspend fun deleteTask(id: Int)

    suspend fun getTaskById(id: Int): TaskItem?

    fun getTasks(): Flow<List<TaskItem>>
}