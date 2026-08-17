package com.aliayali.data.repository

import com.aliayali.data.config.MarketConfig.defaultCoinIds
import com.aliayali.data.error.asAppError
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketData
import com.aliayali.model.result.AppResult
import com.aliayali.network.CoinGeckoNetworkDataSource
import com.aliayali.network.model.CoinGeckoCoinDto
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class MarketRepositoryImpl @Inject constructor(
    private val networkDataSource: CoinGeckoNetworkDataSource,
) : MarketRepository {

    override suspend fun getMarketData(): AppResult<MarketData> {
        return try {
            val coins = networkDataSource
                .getMarkets(
                    ids = defaultCoinIds.joinToString(",")
                )
                .map(CoinGeckoCoinDto::asModel)
            AppResult.Success(
                data = MarketData(
                    coins = coins,
                ),
            )
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            AppResult.Failure(
                error = error.asAppError(),
            )
        }
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