package com.aliayali.database.di

import android.content.Context
import androidx.room.Room
import com.aliayali.database.DaricDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDaricDatabase(
        @ApplicationContext context: Context,
    ): DaricDatabase =
        Room.databaseBuilder(
            context,
            DaricDatabase::class.java,
            "daric_database",
        ).build()
}