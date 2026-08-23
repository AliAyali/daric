package com.aliayali.domain

import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.HomeMarketData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class GetHomeMarketDataUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val marketAssetRepository: MarketAssetRepository,
    private val getMarketOverviewUseCase: GetMarketOverviewUseCase,
) {

    fun observeHomeMarketData(): Flow<HomeMarketData> {
        return combine(
            marketRepository.observeMarketData(),
            marketAssetRepository.observeMarketAssets(),
        ) { marketData, marketAssets ->

            val hasRequiredAssets =
                marketAssets.any { it.symbol == "USD" } &&
                        marketAssets.any { it.symbol == "IR_GOLD_18K" } &&
                        marketAssets.any { it.symbol == "XAUUSD" }
            if (
                marketData.coins.isEmpty() ||
                !hasRequiredAssets
            ) {
                null
            } else {
                val marketOverview = getMarketOverviewUseCase(
                    marketData = marketData,
                    marketAssets = marketAssets,
                )

                HomeMarketData(
                    marketOverview = marketOverview,
                    coins = marketData.coins,
                    marketAssets = marketAssets,
                )
            }
        }.filterNotNull()
    }
}