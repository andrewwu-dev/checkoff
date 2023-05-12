package com.andrew.checkoff.core.data

import com.andrew.checkoff.core.database.dao.TaskDao
import com.andrew.checkoff.core.database.model.asDomainModel
import com.andrew.checkoff.core.model.TaskItem
import com.andrew.checkoff.core.model.asDatabaseModel
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
        taskDao.insert(task.asDatabaseModel())
    }

    override suspend fun updateTask(task: TaskItem) {
        taskDao.update(task.asDatabaseModel())
    }

    override suspend fun deleteTask(task: TaskItem) {
        taskDao.delete(task.asDatabaseModel())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getTaskById(id: Int): Flow<TaskItem?> {
        return taskDao.getTaskById(id).mapLatest { it?.asDomainModel() }
    }

    // val remoteFlow = flow { emit(apiService.getTaskById(id).asDomainModel()) }
    //        val localFlow = taskDao.getTaskById(id).mapLatest { it?.asDomainModel() }
    //        return flowOf(localFlow, remoteFlow).flattenMerge()

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getTasks(): Flow<List<TaskItem>> {
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