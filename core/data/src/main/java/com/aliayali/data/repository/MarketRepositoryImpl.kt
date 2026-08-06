package com.aliayali.data.repository

import com.aliayali.data.datasource.FakeMarketDataSource
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.data.MarketOverview
import com.aliayali.model.data.MarketSection
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val fakeDataSource: FakeMarketDataSource
) : MarketRepository {
    override suspend fun getMarketOverview(): MarketOverview {
        return fakeDataSource.getOverview()
    }

    override suspend fun getMarketSections(): List<MarketSection> {
        return fakeDataSource.getSections()
    }
}