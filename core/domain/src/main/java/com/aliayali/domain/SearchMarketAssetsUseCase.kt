package com.aliayali.domain

import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.market.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class SearchMarketAssetsUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val marketAssetRepository: MarketAssetRepository,
) {

    operator fun invoke(
        query: String,
    ): Flow<List<SearchResult>> {

        val normalizedQuery = query.trim()

        return combine(
            marketRepository.observeMarketData(),
            marketAssetRepository.observeMarketAssets(),
        ) { marketData, marketAssets ->

            val dollarToToman = marketAssets
                .firstOrNull { it.id == USD_ASSET_ID }
                ?.price

            val coins = marketData.coins
                .asSequence()
                .filter { coin ->
                    coin.name.contains(
                        normalizedQuery,
                        ignoreCase = true,
                    ) ||
                            coin.symbol.contains(
                                normalizedQuery,
                                ignoreCase = true,
                            )
                }
                .map { coin ->
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

            val assets = marketAssets
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

            (coins + assets)
                .sortedWith(
                    compareBy(
                        { it.name.lowercase() },
                        { it.symbol.lowercase() },
                    ),
                )
                .toList()
        }
    }

    private companion object {
        const val USD_ASSET_ID = "USD"
    }
}