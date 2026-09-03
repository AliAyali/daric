package com.aliayali.market.model

sealed interface MarketItemUiModel {

    data class Coin(
        val id: String,
        val name: String,
        val symbol: String,
        val imageUrl: String?,
        val formattedDollarPrice: String?,
        val formattedTomanPrice: String?,
        val formattedChange: String,
        val isPositive: Boolean,
    ) : MarketItemUiModel

    data class MarketAsset(
        val id: String,
        val name: String,
        val symbol: String,
        val formattedPrice: String,
        val formattedChange: String,
        val isPositive: Boolean,
    ) : MarketItemUiModel
}