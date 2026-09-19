package com.aliayali.data.analyzer

import com.aliayali.model.analysis.MarketSignal
import com.aliayali.model.analysis.MarketSignalType
import com.aliayali.model.analysis.MarketSnapshot
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import javax.inject.Inject

class MarketScoreCalculator @Inject constructor() {

    fun createSignals(
        snapshot: MarketSnapshot,
    ): List<MarketSignal> {
        return listOf(
            MarketSignal(
                type = MarketSignalType.USD,
                score = changeScore(snapshot.usd.changePercent),
                weight = 0.15,
            ),
            MarketSignal(
                type = MarketSignalType.GOLD_18K,
                score = changeScore(snapshot.gold18.changePercent),
                weight = 0.15,
            ),
            MarketSignal(
                type = MarketSignalType.GOLD_OUNCE,
                score = changeScore(snapshot.goldOunce.changePercent),
                weight = 0.10,
            ),
            MarketSignal(
                type = MarketSignalType.CRYPTO,
                score = cryptoScore(snapshot.coins),
                weight = 0.30,
            ),
            MarketSignal(
                type = MarketSignalType.LOCAL_MARKET,
                score = localMarketScore(snapshot.marketAssets),
                weight = 0.15,
            ),
        )
    }

    fun calculateScore(
        signals: List<MarketSignal>,
    ): Double {
        return signals.sumOf {
            it.score * it.weight
        }
    }

    private fun changeScore(
        change: Double?,
    ): Double {
        val value = change ?: return 0.0

        return (value / 5.0)
            .coerceIn(-1.0, 1.0)
    }

    private fun cryptoScore(
        coins: List<Coin>,
    ): Double {
        val changes = coins
            .mapNotNull { it.changePercent24h }

        if (changes.isEmpty()) return 0.0

        val positiveCount = changes.count { it > 0 }
        val negativeCount = changes.count { it < 0 }
        val total = changes.size

        val breadth =
            (positiveCount - negativeCount).toDouble() / total

        val averageChange =
            changes.average().let(::changeScore)

        return (breadth * 0.6 + averageChange * 0.4)
            .coerceIn(-1.0, 1.0)
    }

    private fun localMarketScore(
        assets: List<MarketAsset>,
    ): Double {
        val changes = assets
            .mapNotNull { it.changePercent }

        if (changes.isEmpty()) return 0.0

        val positiveCount = changes.count { it > 0 }
        val negativeCount = changes.count { it < 0 }
        val total = changes.size

        val breadth =
            (positiveCount - negativeCount).toDouble() / total

        val averageChange =
            changes.average().let(::changeScore)

        return (breadth * 0.6 + averageChange * 0.4)
            .coerceIn(-1.0, 1.0)
    }
}