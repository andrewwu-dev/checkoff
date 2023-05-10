package com.andrew.checkoff.core.data

import com.andrew.checkoff.core.database.dao.TaskDao
import com.andrew.checkoff.core.database.model.asExternalModel
import com.andrew.checkoff.core.model.TaskItem
import com.andrew.checkoff.core.model.asDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun addTask(task: TaskItem) {
        taskDao.insert(task.asDatabaseModel())
    }

    override suspend fun deleteTask(task: TaskItem) {
        taskDao.delete(task.asDatabaseModel())
    }

    override suspend fun getTaskById(id: Int): TaskItem? {
        return taskDao.getTaskById(id)?.asExternalModel()
    }

    override fun getTasks(): Flow<List<TaskItem>> = taskDao.getTasks().mapLatest {
        it.map { task -> task.asExternalModel() }
    }
}