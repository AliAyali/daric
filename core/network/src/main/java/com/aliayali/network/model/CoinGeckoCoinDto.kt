package com.aliayali.network.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class CoinGeckoCoinDto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerialName("current_price")
    val currentPrice: Double?,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class CoinMarketChartDto(
    val prices: List<List<Double>>,
    @SerialName("market_caps")
    val marketCaps: List<List<Double>>,
    @SerialName("total_volumes")
    val totalVolumes: List<List<Double>>,
)