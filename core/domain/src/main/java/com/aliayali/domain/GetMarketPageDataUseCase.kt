package com.aliayali.domain

import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.market.MarketPageData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMarketPageDataUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val marketAssetRepository: MarketAssetRepository,
) {
    operator fun invoke(): Flow<MarketPageData> {
        return combine(
            marketRepository.observeMarketData(),
            marketAssetRepository.observeMarketAssets(),
        ) { marketData, marketAssets ->
            MarketPageData(
                coins = marketData.coins,
                marketAssets = marketAssets,
            )
        }
    }
}