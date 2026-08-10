package com.aliayali.domain.repository

import com.aliayali.model.data.MarketData

interface MarketRepository {
    suspend fun getMarketData(): MarketData
}