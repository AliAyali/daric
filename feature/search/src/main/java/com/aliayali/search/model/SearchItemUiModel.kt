package com.aliayali.search.model

sealed interface SearchItemUiModel {

    val id: String
    val symbol: String
    val name: String

    data class Coin(
        override val id: String,
        override val symbol: String,
        override val name: String,
        val imageUrl: String?,
        val formattedDollarPrice: String?,
        val formattedTomanPrice: String?,
        val formattedChange: String,
        val isPositive: Boolean,
    ) : SearchItemUiModel

    data class MarketAsset(
        override val id: String,
        override val symbol: String,
        override val name: String,
        val formattedPrice: String,
        val formattedChange: String,
        val isPositive: Boolean,
    ) : SearchItemUiModel
}