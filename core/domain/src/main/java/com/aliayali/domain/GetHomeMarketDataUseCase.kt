package com.aliayali.domain

import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.HomeMarketData
import javax.inject.Inject

class GetHomeMarketDataUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val marketAssetRepository: MarketAssetRepository,
    private val getMarketOverviewUseCase: GetMarketOverviewUseCase,
) {

    suspend operator fun invoke(): HomeMarketData {
        val marketData = marketRepository.getMarketData()

        val marketAssets = marketAssetRepository.getMarketAssets()

        val marketOverview = getMarketOverviewUseCase(
            marketData = marketData,
            marketAssets = marketAssets,
        )

        return HomeMarketData(
            marketOverview = marketOverview,
            coins = marketData.coins,
            marketAssets = marketAssets,
        )
    }
}