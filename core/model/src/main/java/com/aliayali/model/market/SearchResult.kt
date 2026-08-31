package com.aliayali.model.market

sealed interface SearchResult {

    val id: String
    val symbol: String
    val name: String

    data class CoinResult(
        override val id: String,
        override val symbol: String,
        override val name: String,
        val imageUrl: String,
        val price: Double?,
        val changePercent24h: Double?,
        val dollarToToman: Double?,
    ) : SearchResult

    data class MarketAssetResult(
        override val id: String,
        override val symbol: String,
        override val name: String,
        val price: Double?,
        val changePercent: Double?,
        val unit: String,
    ) : SearchResult
}