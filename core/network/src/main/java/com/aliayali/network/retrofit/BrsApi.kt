package com.aliayali.network.retrofit

import com.aliayali.network.model.BrsMarketResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BrsApi {

    @GET("Market/Gold_Currency.php")
    suspend fun getMarket(
        @Query("key") apiKey: String,
    ): BrsMarketResponseDto
}