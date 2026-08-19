package com.aliayali.database.di

import com.aliayali.database.DaricDatabase
import com.aliayali.database.dao.CoinDao
import com.aliayali.database.dao.MarketAssetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun provideCoinDao(
        database: DaricDatabase,
    ): CoinDao = database.coinDao()

    @Provides
    fun provideMarketAssetDao(
        database: DaricDatabase,
    ): MarketAssetDao = database.marketAssetDao()
}