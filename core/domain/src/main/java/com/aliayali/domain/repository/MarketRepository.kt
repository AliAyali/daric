package com.aliayali.domain.repository

import com.aliayali.model.market.MarketData

interface MarketRepository {
    suspend fun getMarketData(): MarketData
}