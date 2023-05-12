package com.andrew.checkoff

import com.andrew.checkoff.core.data.TaskRepository
import com.andrew.checkoff.core.model.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeTaskRepository : TaskRepository {
    val tasks = mutableListOf<TaskItem>(
        TaskItem(1, "Task 1", "Description 1", false),
        TaskItem(2, "Task 2", "Description 2", true),
    )

    override suspend fun addTask(task: TaskItem) {
        // add or replace TaskItem to mimic upsert
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index == -1) {
            tasks.add(task)
        } else {
            tasks[index] = task
        }
    }

    override suspend fun updateTask(task: TaskItem) {
        val index = tasks.indexOfFirst { it.id == task.id }
        tasks[index] = task
    }

    override suspend fun deleteTask(task: TaskItem) {
        tasks.remove(task)
    }

    override suspend fun getTaskById(id: Int): Flow<TaskItem?> {
        return flow { emit(tasks.find { it.id == id }) }
    }

    override suspend fun getTasks(): Flow<List<TaskItem>> {
        return flow { emit(tasks) }
    }

}