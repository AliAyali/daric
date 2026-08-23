package com.aliayali.data.di

import com.aliayali.data.local.CoinLocalDataSource
import com.aliayali.data.local.CoinLocalDataSourceImpl
import com.aliayali.data.local.MarketAssetLocalDataSource
import com.aliayali.data.local.MarketAssetLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindCoinLocalDataSource(
        impl: CoinLocalDataSourceImpl,
    ): CoinLocalDataSource

    @Binds
    abstract fun bindMarketAssetLocalDataSource(
        impl: MarketAssetLocalDataSourceImpl,
    ): MarketAssetLocalDataSource
}