package com.aliayali.data.repository

import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.model.market.MarketAsset
import com.aliayali.network.BrsNetworkDataSource
import com.aliayali.network.model.BrsMarketItemDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketAssetRepositoryImpl @Inject constructor(
    private val networkDataSource: BrsNetworkDataSource,
) : MarketAssetRepository {

    override suspend fun getMarketAssets(): List<MarketAsset> {
        val market = networkDataSource.getMarket()

        return (market.gold + market.currency)
            .map(BrsMarketItemDto::asModel)
    }
}

fun BrsMarketItemDto.asModel(): MarketAsset =
    MarketAsset(
        id = symbol,
        symbol = symbol,
        name = name,
        price = price,
        changePercent = changePercent,
        unit = unit,
    )