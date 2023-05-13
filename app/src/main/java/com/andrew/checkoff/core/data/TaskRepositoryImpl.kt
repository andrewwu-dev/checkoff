package com.andrew.checkoff.core.data

import com.andrew.checkoff.core.database.dao.TaskDao
import com.andrew.checkoff.core.database.model.asDomainModel
import com.andrew.checkoff.core.model.TaskItem
import com.andrew.checkoff.core.model.asDatabaseModel
import com.andrew.checkoff.core.model.asNetworkModel
import com.andrew.checkoff.core.network.ApiService
import com.andrew.checkoff.core.network.asDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val apiService: ApiService,
) : TaskRepository {
    override suspend fun addTask(task: TaskItem) {
        apiService.addTask(task.asNetworkModel())
        taskDao.insert(task.asDatabaseModel())
    }

    override suspend fun updateTask(task: TaskItem) {
        apiService.updateTask(task.id ?: 1, task.asNetworkModel())
        taskDao.update(task.asDatabaseModel())
    }

    override suspend fun deleteTask(task: TaskItem) {
        apiService.deleteTask(task.id ?: 1)
        taskDao.delete(task.asDatabaseModel())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getTaskById(id: Int): Flow<TaskItem?> {
        return taskDao.getTaskById(id).mapLatest { it?.asDomainModel() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getTasks(): Flow<List<TaskItem>> {
        // For demo purposes, fetch the first 5 tasks to simulate the app retrieving tasks from an API
        // Ideally task id on server would be the same as the id on the database so if
        // the same task is retrieved from the server, taskDao updates the existing task rather than addin a new one.
        for (i in 1..5) {
            apiService.getTaskById(i).asDomainModel().let { task ->
                taskDao.insert(task.asDatabaseModel())
            }
        }
        return taskDao.getTasks().mapLatest {
            it.map { task -> task.asDomainModel() }
        }
    }
}