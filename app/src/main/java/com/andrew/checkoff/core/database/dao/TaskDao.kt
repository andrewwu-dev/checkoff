package com.andrew.checkoff.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andrew.checkoff.core.database.model.TaskItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskItemEntity)

    @Delete
    suspend fun delete(task: TaskItemEntity)

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskItemEntity?

    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<TaskItemEntity>>
}