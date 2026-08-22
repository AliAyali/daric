package com.aliayali.data.di

import com.aliayali.data.network.ConnectivityNetworkMonitor
import com.aliayali.domain.repository.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkMonitorModule {

    @Binds
    abstract fun bindNetworkMonitor(
        impl: ConnectivityNetworkMonitor,
    ): NetworkMonitor
}