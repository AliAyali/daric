package com.aliayali.domain

import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.data.MarketOverview
import javax.inject.Inject

class GetMarketOverviewUseCase @Inject constructor(
    private val repository: MarketRepository,
) {

    suspend operator fun invoke(): MarketOverview {
        return repository.getMarketOverview()
    }
}