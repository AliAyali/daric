package com.aliayali.network.retrofit

import com.aliayali.network.model.CoinGeckoCoinDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("coins/markets")
    suspend fun getMarkets(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("ids") ids: String,
        @Query("price_change_percentage")
        priceChangePercentage: String = "24h",
    ): List<CoinGeckoCoinDto>
}