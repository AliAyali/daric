package com.aliayali.data.di

import com.aliayali.domain.MarketAnalyzer
import com.aliayali.data.analyzer.RuleBasedMarketAnalyzer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyzerModule {

    @Binds
    @Singleton
    abstract fun bindMarketAnalyzer(
        analyzer: RuleBasedMarketAnalyzer,
    ): MarketAnalyzer
}