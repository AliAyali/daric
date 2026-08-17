package com.aliayali.network

import com.aliayali.network.error.safeNetworkCall
import com.aliayali.network.model.BrsMarketResponseDto
import javax.inject.Inject
import javax.inject.Singleton

interface BrsNetworkDataSource {
    suspend fun getMarket(): BrsMarketResponseDto
}

@Singleton
class RetrofitBrsNetworkDataSource @Inject constructor(
    private val api: BrsApi,
) : BrsNetworkDataSource {

    override suspend fun getMarket(): BrsMarketResponseDto =
        safeNetworkCall {
            api.getMarket(
                apiKey = BuildConfig.BRS_API_KEY,
            )
        }
}