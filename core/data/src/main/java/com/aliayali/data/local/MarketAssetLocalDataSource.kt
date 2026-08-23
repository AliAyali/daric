package com.aliayali.data.local

import com.aliayali.model.market.MarketAsset
import kotlinx.coroutines.flow.Flow

interface MarketAssetLocalDataSource {

    fun observeMarketAssets(): Flow<List<MarketAsset>>

    suspend fun saveMarketAssets(
        assets: List<MarketAsset>,
    )
}