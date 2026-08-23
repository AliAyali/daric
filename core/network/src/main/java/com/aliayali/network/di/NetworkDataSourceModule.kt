package com.aliayali.network.di

import com.aliayali.network.BrsNetworkDataSource
import com.aliayali.network.CoinGeckoNetworkDataSource
import com.aliayali.network.RetrofitBrsNetworkDataSource
import com.aliayali.network.RetrofitCoinGeckoNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindCoinGeckoNetworkDataSource(
        impl: RetrofitCoinGeckoNetworkDataSource,
    ): CoinGeckoNetworkDataSource

    @Binds
    @Singleton
    abstract fun bindBrsNetworkDataSource(
        impl: RetrofitBrsNetworkDataSource,
    ): BrsNetworkDataSource
}