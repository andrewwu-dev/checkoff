package com.andrew.checkoff.core.database

import com.andrew.checkoff.core.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideTaskDao(
        checkoffDatabase: CheckoffDatabase
    ): TaskDao = checkoffDatabase.taskDao
}