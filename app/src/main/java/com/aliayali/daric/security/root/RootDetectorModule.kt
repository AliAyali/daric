package com.aliayali.daric.security.root

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RootDetectorModule {

    @Binds
    @Singleton
    abstract fun bindRootDetector(
        implementation: AndroidRootDetector,
    ): RootDetector
}