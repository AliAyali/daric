package com.aliayali.datastore.di

import com.aliayali.datastore.UserPreferencesRepositoryImpl
import com.aliayali.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPreferencesRepositoryModule {

    @Binds
    abstract fun bindUserPreferencesRepository(
        repository: UserPreferencesRepositoryImpl,
    ): UserPreferencesRepository
}