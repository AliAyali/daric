package com.aliayali.domain.repository

import com.aliayali.model.market.MarketAsset

interface MarketAssetRepository {
    suspend fun getMarketAssets(): List<MarketAsset>
}