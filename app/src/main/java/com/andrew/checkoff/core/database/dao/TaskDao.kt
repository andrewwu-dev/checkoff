package com.andrew.checkoff.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.andrew.checkoff.core.database.model.TaskItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun insert(task: TaskItemEntity)

    @Upsert
    suspend fun insertAll(tasks: List<TaskItemEntity>)

    @Update
    suspend fun update(task: TaskItemEntity)

    @Delete
    suspend fun delete(task: TaskItemEntity)

    @Query("SELECT * FROM task WHERE id = :id")
    fun getTaskById(id: Int): Flow<TaskItemEntity?>

    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<TaskItemEntity>>
}