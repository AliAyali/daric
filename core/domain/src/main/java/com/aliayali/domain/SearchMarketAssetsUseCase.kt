package com.aliayali.domain

import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.market.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMarketAssetsUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val marketAssetRepository: MarketAssetRepository,
) {

    operator fun invoke(
        query: String,
    ): Flow<List<SearchResult>> = flow {

        val normalizedQuery = query.trim()

        if (normalizedQuery.isBlank()) {
            emit(emptyList())
            return@flow
        }

        val coins = marketRepository
            .searchCoins(normalizedQuery)
            .map { coin ->

                val dollarToToman = marketAssetRepository
                    .observeMarketAssets()
                    .first()
                    .firstOrNull { it.id == USD_ASSET_ID }
                    ?.price

                SearchResult.CoinResult(
                    id = coin.id,
                    symbol = coin.symbol,
                    name = coin.name,
                    imageUrl = coin.imageUrl,
                    price = coin.price,
                    changePercent24h = coin.changePercent24h,
                    dollarToToman = dollarToToman,
                )
            }

        val marketAssets = marketAssetRepository
            .observeMarketAssets()
            .first()
            .asSequence()
            .filter { asset ->
                asset.name.contains(
                    normalizedQuery,
                    ignoreCase = true,
                ) ||
                        asset.symbol.contains(
                            normalizedQuery,
                            ignoreCase = true,
                        )
            }
            .map { asset ->
                SearchResult.MarketAssetResult(
                    id = asset.id,
                    symbol = asset.symbol,
                    name = asset.name,
                    price = asset.price,
                    changePercent = asset.changePercent,
                    unit = asset.unit,
                )
            }
            .toList()

        emit(
            (coins + marketAssets)
                .sortedWith(
                    compareBy(
                        { it.name.lowercase() },
                        { it.symbol.lowercase() },
                    ),
                ),
        )
    }

    private companion object {
        const val USD_ASSET_ID = "USD"
    }
}