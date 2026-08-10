package com.aliayali.domain

import com.aliayali.domain.repository.MarketRepository
import javax.inject.Inject

class GetMarketCoinsUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    suspend operator fun invoke() =
        repository.getMarketData()
}