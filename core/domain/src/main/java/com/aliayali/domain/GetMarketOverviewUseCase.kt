package com.aliayali.domain

import com.aliayali.model.market.MarketAsset
import com.aliayali.model.market.MarketData
import com.aliayali.model.analysis.MarketOverview
import com.aliayali.model.analysis.MarketSnapshot
import javax.inject.Inject

class GetMarketOverviewUseCase @Inject constructor(
    private val marketAnalyzer: MarketAnalyzer,
) {

    suspend operator fun invoke(
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

        val btc = marketData.coins.first {
            it.symbol.equals("btc", ignoreCase = true)
        }

        val snapshot = MarketSnapshot(
            usd = usd,
            gold18 = gold18,
            goldOunce = goldOunce,
            btc = btc,
        )

        val analysis = marketAnalyzer.analyze(snapshot)

        return MarketOverview(
            marketStatus = analysis.status,
            insightTitle = analysis.title,
            insightDescription = analysis.description,
            usd = usd,
            gold18 = gold18,
        )
    }
}