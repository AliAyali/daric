package com.aliayali.network.retrofit

import com.aliayali.network.model.CoinGeckoCoinDto
import com.aliayali.network.model.CoinGeckoSearchResponseDto
import com.aliayali.network.model.CoinMarketChartDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("coins/markets")
    suspend fun getMarkets(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("ids") ids: String,
        @Query("price_change_percentage")
        priceChangePercentage: String = "24h",
    ): List<CoinGeckoCoinDto>

    @GET("search")
    suspend fun search(
        @Query("query") query: String,
    ): CoinGeckoSearchResponseDto

    @GET("coins/{id}/market_chart")
    suspend fun getMarketChart(
        @Path("id") id: String,
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("days") days: Int = 1,
    ): CoinMarketChartDto
}