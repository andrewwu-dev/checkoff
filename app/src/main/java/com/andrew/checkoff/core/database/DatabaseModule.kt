package com.andrew.checkoff.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideCheckoffDatabase(
        @ApplicationContext context: Context
    ): CheckoffDatabase = Room.databaseBuilder(
        context,
        CheckoffDatabase::class.java,
        "checkoff_database"
    ).build()
}