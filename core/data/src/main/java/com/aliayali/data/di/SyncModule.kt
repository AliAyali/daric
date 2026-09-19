package com.aliayali.data.di

import com.aliayali.data.sync.MarketSyncerImpl
import com.aliayali.data.sync.NewsSyncerImpl
import com.aliayali.domain.sync.MarketSyncer
import com.aliayali.domain.sync.NewsSyncer
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

    @Binds
    abstract fun bindNewsSyncer(
        impl: NewsSyncerImpl,
    ): NewsSyncer
}