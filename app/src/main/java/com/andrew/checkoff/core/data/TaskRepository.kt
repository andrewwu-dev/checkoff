package com.andrew.checkoff.core.data

import com.andrew.checkoff.core.model.TaskItem
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: TaskItem)

    suspend fun updateTask(task: TaskItem)

    suspend fun deleteTask(task: TaskItem)

    suspend fun getTaskById(id: Int): Flow<TaskItem?>

    suspend fun getTasks(): Flow<List<TaskItem>>
}