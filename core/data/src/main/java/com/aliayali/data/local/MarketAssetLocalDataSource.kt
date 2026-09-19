package com.aliayali.data.local

import com.aliayali.model.market.MarketAsset
import kotlinx.coroutines.flow.Flow

interface MarketAssetLocalDataSource {
    fun observeMarketAsset(id: String): Flow<MarketAsset?>
    fun observeMarketAssets(): Flow<List<MarketAsset>>

    suspend fun saveMarketAssets(
        assets: List<MarketAsset>,
    )
}