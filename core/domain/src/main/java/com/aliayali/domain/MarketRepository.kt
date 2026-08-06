package com.aliayali.domain

import com.aliayali.model.data.MarketOverview
import com.aliayali.model.data.MarketSection

interface MarketRepository {
    suspend fun getMarketOverview(): MarketOverview
    suspend fun getMarketSections(): List<MarketSection>
}