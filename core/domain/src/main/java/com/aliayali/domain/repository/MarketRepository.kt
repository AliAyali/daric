package com.aliayali.domain.repository

import com.aliayali.common.result.AppResult
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import com.aliayali.model.market.MarketData
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    fun observeMarketData(
        id: String,
    ): Flow<Coin?>

    fun observeMarketData(): Flow<MarketData>

    suspend fun syncMarketData(): AppResult<Unit>
}