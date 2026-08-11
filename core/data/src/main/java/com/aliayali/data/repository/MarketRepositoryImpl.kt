package com.aliayali.data.repository

import com.aliayali.data.config.MarketConfig.defaultCoinIds
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.data.Coin
import com.aliayali.model.data.MarketData
import com.aliayali.network.CoinGeckoNetworkDataSource
import com.aliayali.network.model.CoinGeckoCoinDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketRepositoryImpl @Inject constructor(
    private val networkDataSource: CoinGeckoNetworkDataSource,
) : MarketRepository {

    override suspend fun getMarketData(): MarketData {
        val coins = networkDataSource
            .getMarkets(
                ids = defaultCoinIds.joinToString(",")
            )
            .map(CoinGeckoCoinDto::asModel)

        return MarketData(
            coins = coins,
        )
    }
}

fun CoinGeckoCoinDto.asModel(): Coin =
    Coin(
        id = id,
        symbol = symbol,
        name = name,
        price = currentPrice,
        changePercent24h = priceChangePercentage24h,
        imageUrl = image,
    )