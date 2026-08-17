package com.aliayali.domain.repository

import com.aliayali.model.market.MarketData
import com.aliayali.model.result.AppResult

interface MarketRepository {
    suspend fun getMarketData(): AppResult<MarketData>
}