package com.aliayali.data.di

import com.aliayali.data.repository.MarketAssetRepositoryImpl
import com.aliayali.data.repository.MarketRepositoryImpl
import com.aliayali.data.repository.NewsRepositoryImpl
import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMarketRepository(
        impl: MarketRepositoryImpl,
    ): MarketRepository

    @Binds
    @Singleton
    abstract fun bindMarketAssetRepository(
        impl: MarketAssetRepositoryImpl,
    ): MarketAssetRepository

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        impl: NewsRepositoryImpl,
    ): NewsRepository
}