package com.aliayali.domain

import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.market.MarketPricePoint
import javax.inject.Inject

class GetCoinPriceHistoryUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
) {
    suspend operator fun invoke(
        coinId: String,
        days: Int = 1,
    ): List<MarketPricePoint> =
        marketRepository.getCoinPriceHistory(
            id = coinId,
            days = days,
        )
}