package com.aliayali.domain.repository

import com.aliayali.model.market.MarketData
import com.aliayali.model.result.AppResult
import kotlinx.coroutines.flow.Flow

interface MarketRepository {

    fun observeMarketData(): Flow<MarketData>

    suspend fun syncMarketData(): AppResult<Unit>
}