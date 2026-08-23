package com.aliayali.domain.repository

import com.aliayali.common.result.AppResult
import com.aliayali.model.market.MarketData
import kotlinx.coroutines.flow.Flow

interface MarketRepository {

    fun observeMarketData(): Flow<MarketData>

    suspend fun syncMarketData(): AppResult<Unit>
}