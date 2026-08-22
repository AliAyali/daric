package com.aliayali.data.repository

import com.aliayali.data.config.MarketConfig.defaultCoinIds
import com.aliayali.data.error.asAppError
import com.aliayali.data.local.CoinLocalDataSource
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketData
import com.aliayali.common.result.AppResult
import com.aliayali.network.CoinGeckoNetworkDataSource
import com.aliayali.network.model.CoinGeckoCoinDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
internal class MarketRepositoryImpl @Inject constructor(
    private val networkDataSource: CoinGeckoNetworkDataSource,
    private val localDataSource: CoinLocalDataSource,
) : MarketRepository {

    override fun observeMarketData(): Flow<MarketData> =
        localDataSource
            .observeCoins()
            .map(::MarketData)

    override suspend fun syncMarketData(): AppResult<Unit> {
        return try {
            val coins = networkDataSource
                .getMarkets(
                    ids = defaultCoinIds.joinToString(","),
                )
                .map(CoinGeckoCoinDto::asModel)

            localDataSource.saveCoins(coins)

            AppResult.Success(Unit)
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