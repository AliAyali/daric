package com.aliayali.domain.repository

import com.aliayali.common.result.AppResult
import com.aliayali.model.market.MarketAsset
import kotlinx.coroutines.flow.Flow

interface MarketAssetRepository {

    fun observeMarketAssets(): Flow<List<MarketAsset>>

    suspend fun syncMarketAssets(): AppResult<Unit>
}