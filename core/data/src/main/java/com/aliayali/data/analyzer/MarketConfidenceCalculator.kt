package com.aliayali.data.analyzer

import com.aliayali.model.analysis.MarketSignal
import javax.inject.Inject
import kotlin.math.abs

class MarketConfidenceCalculator @Inject constructor() {

    fun calculate(
        signals: List<MarketSignal>,
    ): Double {
        if (signals.isEmpty()) return 0.0

        val totalWeight = signals.sumOf { it.weight }

        if (totalWeight == 0.0) return 0.0

        val strength = signals.sumOf {
            abs(it.score) * it.weight
        } / totalWeight

        val agreement = calculateAgreement(signals)

        return (strength * 0.6 + agreement * 0.4).coerceIn(0.0, 1.0)
    }

    private fun calculateAgreement(
        signals: List<MarketSignal>,
    ): Double {
        if (signals.isEmpty()) return 0.0

        val positiveWeight = signals.filter { it.score > 0 }.sumOf { it.weight }

        val negativeWeight = signals.filter { it.score < 0 }.sumOf { it.weight }

        val totalWeight = signals.sumOf { it.weight }

        if (totalWeight == 0.0) return 0.0

        return abs(
            positiveWeight - negativeWeight
        ) / totalWeight
    }
}