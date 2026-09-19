package com.aliayali.domain

import com.aliayali.model.analysis.MarketOverview
import com.aliayali.model.analysis.MarketSnapshot
import com.aliayali.model.market.MarketAsset
import com.aliayali.model.market.MarketData
import javax.inject.Inject

class GetMarketOverviewUseCase @Inject constructor(
    private val marketAnalyzer: MarketAnalyzer,
) {

    operator fun invoke(
        marketData: MarketData,
        marketAssets: List<MarketAsset>,
    ): MarketOverview {

        val usd = marketAssets.first {
            it.symbol == "USD"
        }

        val gold18 = marketAssets.first {
            it.symbol == "IR_GOLD_18K"
        }

        val goldOunce = marketAssets.first {
            it.symbol == "XAUUSD"
        }

        val snapshot = MarketSnapshot(
            usd = usd,
            gold18 = gold18,
            goldOunce = goldOunce,
            coins = marketData.coins,
            marketAssets = marketAssets,
        )

        val analysis = marketAnalyzer.analyze(snapshot)

        return MarketOverview(
            analysis = analysis,
            usd = usd,
            gold18 = gold18,
        )
    }
}