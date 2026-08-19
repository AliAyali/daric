package com.aliayali.domain.repository

import com.aliayali.model.market.MarketAsset
import com.aliayali.model.result.AppResult
import kotlinx.coroutines.flow.Flow

interface MarketAssetRepository {

    fun observeMarketAssets(): Flow<List<MarketAsset>>

    suspend fun syncMarketAssets(): AppResult<Unit>
}