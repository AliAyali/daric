package com.aliayali.marketdetail.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class MarketDetailNavKey(
    val assetId: String,
    val assetType: MarketDetailAssetType,
) : NavKey

@Serializable
enum class MarketDetailAssetType {
    CRYPTO,
    MARKET,
}