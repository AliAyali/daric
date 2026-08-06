package com.aliayali.domain


import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.data.MarketSection
import javax.inject.Inject

class GetMarketSectionsUseCase @Inject constructor(
    private val repository: MarketRepository,
) {

    suspend operator fun invoke(): List<MarketSection> {
        return repository.getMarketSections()
    }
}