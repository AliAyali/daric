package com.aliayali.domain

import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.HomeMarketData
import com.aliayali.model.result.AppResult
import javax.inject.Inject

class GetHomeMarketDataUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val marketAssetRepository: MarketAssetRepository,
    private val getMarketOverviewUseCase: GetMarketOverviewUseCase,
) {

    suspend operator fun invoke(): AppResult<HomeMarketData> {

        val marketResult = marketRepository.getMarketData()

        if (marketResult is AppResult.Failure) {
            return AppResult.Failure(
                error = marketResult.error,
            )
        }

        val assetResult = marketAssetRepository.getMarketAssets()

        if (assetResult is AppResult.Failure) {
            return AppResult.Failure(
                error = assetResult.error,
            )
        }

        val marketData =
            (marketResult as AppResult.Success).data

        val marketAssets =
            (assetResult as AppResult.Success).data

        val marketOverview = getMarketOverviewUseCase(
            marketData = marketData,
            marketAssets = marketAssets,
        )

        return AppResult.Success(
            data = HomeMarketData(
                marketOverview = marketOverview,
                coins = marketData.coins,
                marketAssets = marketAssets,
            ),
        )
    }
}