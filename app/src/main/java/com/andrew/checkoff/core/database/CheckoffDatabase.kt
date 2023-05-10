package com.andrew.checkoff.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andrew.checkoff.core.database.dao.TaskDao
import com.andrew.checkoff.core.database.model.TaskItemEntity

@Database(
    entities = [TaskItemEntity::class],
    version = 1
)
abstract class CheckoffDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
}