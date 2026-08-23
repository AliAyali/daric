package com.aliayali.data.analyzer

import com.aliayali.model.analysis.MarketReason
import com.aliayali.model.analysis.MarketSignal
import com.aliayali.model.analysis.MarketSignalStrength
import com.aliayali.model.analysis.MarketSignalType
import com.aliayali.model.analysis.MarketSnapshot
import javax.inject.Inject
import kotlin.math.abs

class MarketReasonBuilder @Inject constructor() {

    fun build(
        snapshot: MarketSnapshot,
        signals: List<MarketSignal>,
    ): List<MarketReason> {

        return signals
            .sortedByDescending {
                abs(it.score * it.weight)
            }
            .mapNotNull { signal ->

                val strength = signalStrength(signal.score)

                when (signal.type) {

                    MarketSignalType.USD -> {
                        val change = snapshot.usd.changePercent

                        when {
                            change == null || change == 0.0 -> null

                            change > 0 &&
                                    strength == MarketSignalStrength.Strong ->
                                MarketReason.USD_SIGNIFICANT_INCREASE

                            change > 0 ->
                                MarketReason.USD_INCREASE

                            change < 0 &&
                                    strength == MarketSignalStrength.Strong ->
                                MarketReason.USD_SIGNIFICANT_DECREASE

                            change < 0 ->
                                MarketReason.USD_DECREASE

                            else -> null
                        }
                    }

                    MarketSignalType.GOLD_18K -> {
                        val change = snapshot.gold18.changePercent

                        when {
                            change == null || change == 0.0 -> null

                            change > 0 &&
                                    strength == MarketSignalStrength.Strong ->
                                MarketReason.GOLD_18K_SIGNIFICANT_INCREASE

                            change > 0 ->
                                MarketReason.GOLD_18K_INCREASE

                            change < 0 &&
                                    strength == MarketSignalStrength.Strong ->
                                MarketReason.GOLD_18K_SIGNIFICANT_DECREASE

                            change < 0 ->
                                MarketReason.GOLD_18K_DECREASE

                            else -> null
                        }
                    }

                    MarketSignalType.GOLD_OUNCE -> {
                        val change = snapshot.goldOunce.changePercent

                        when {
                            change == null || change == 0.0 -> null

                            change > 0 &&
                                    strength == MarketSignalStrength.Strong ->
                                MarketReason.GOLD_OUNCE_SIGNIFICANT_INCREASE

                            change > 0 ->
                                MarketReason.GOLD_OUNCE_INCREASE

                            change < 0 &&
                                    strength == MarketSignalStrength.Strong ->
                                MarketReason.GOLD_OUNCE_SIGNIFICANT_DECREASE

                            change < 0 ->
                                MarketReason.GOLD_OUNCE_DECREASE

                            else -> null
                        }
                    }

                    MarketSignalType.CRYPTO -> {
                        val changes = snapshot.coins
                            .mapNotNull { it.changePercent24h }

                        if (changes.isEmpty()) {
                            null
                        } else {
                            val positive = changes.count { it > 0 }
                            val negative = changes.count { it < 0 }
                            val total = changes.size

                            when {
                                positive > total * 0.6 -> {
                                    when (strength) {
                                        MarketSignalStrength.Strong ->
                                            MarketReason.CRYPTO_STRONG_POSITIVE

                                        MarketSignalStrength.Moderate ->
                                            MarketReason.CRYPTO_MODERATE_POSITIVE

                                        MarketSignalStrength.Weak ->
                                            MarketReason.CRYPTO_WEAK_POSITIVE
                                    }
                                }

                                negative > total * 0.6 -> {
                                    when (strength) {
                                        MarketSignalStrength.Strong ->
                                            MarketReason.CRYPTO_STRONG_NEGATIVE

                                        MarketSignalStrength.Moderate ->
                                            MarketReason.CRYPTO_MODERATE_NEGATIVE

                                        MarketSignalStrength.Weak ->
                                            MarketReason.CRYPTO_WEAK_NEGATIVE
                                    }
                                }

                                else ->
                                    MarketReason.CRYPTO_MIXED
                            }
                        }
                    }

                    MarketSignalType.LOCAL_MARKET -> {
                        when (strength) {
                            MarketSignalStrength.Strong ->
                                if (signal.score > 0) {
                                    MarketReason.LOCAL_STRONG_POSITIVE
                                } else {
                                    MarketReason.LOCAL_STRONG_NEGATIVE
                                }

                            MarketSignalStrength.Moderate ->
                                if (signal.score > 0) {
                                    MarketReason.LOCAL_MODERATE_POSITIVE
                                } else {
                                    MarketReason.LOCAL_MODERATE_NEGATIVE
                                }

                            MarketSignalStrength.Weak ->
                                MarketReason.LOCAL_WEAK
                        }
                    }
                }
            }
            .take(3)
    }

    private fun signalStrength(
        score: Double,
    ): MarketSignalStrength =
        when {
            abs(score) < 0.30 ->
                MarketSignalStrength.Weak

            abs(score) < 0.70 ->
                MarketSignalStrength.Moderate

            else ->
                MarketSignalStrength.Strong
        }
}