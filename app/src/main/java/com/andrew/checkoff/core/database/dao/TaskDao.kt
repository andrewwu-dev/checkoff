package com.andrew.checkoff.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.andrew.checkoff.core.database.model.TaskItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskItemEntity)

    @Update
    suspend fun update(task: TaskItemEntity)

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskItemEntity?

    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<TaskItemEntity>>
}