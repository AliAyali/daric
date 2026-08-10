package com.aliayali.domain.repository

import com.aliayali.model.data.MarketAsset

interface MarketAssetRepository {
    suspend fun getMarketAssets(): List<MarketAsset>
}