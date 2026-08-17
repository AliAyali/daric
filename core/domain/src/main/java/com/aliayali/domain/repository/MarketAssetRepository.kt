package com.aliayali.domain.repository

import com.aliayali.model.market.MarketAsset
import com.aliayali.model.result.AppResult

interface MarketAssetRepository {
    suspend fun getMarketAssets(): AppResult<List<MarketAsset>>
}