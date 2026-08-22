package com.aliayali.data.di

import com.aliayali.data.sync.MarketSyncerImpl
import com.aliayali.domain.sync.MarketSyncer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SyncModule {

    @Binds
    abstract fun bindMarketSyncer(
        impl: MarketSyncerImpl,
    ): MarketSyncer
}