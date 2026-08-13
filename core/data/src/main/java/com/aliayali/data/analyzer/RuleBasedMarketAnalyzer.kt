package com.aliayali.data.analyzer

import com.aliayali.domain.MarketAnalyzer
import com.aliayali.model.analysis.MarketAnalysis
import com.aliayali.model.analysis.MarketCondition
import com.aliayali.model.analysis.MarketSnapshot
import javax.inject.Inject

class RuleBasedMarketAnalyzer @Inject constructor(
    private val scoreCalculator: MarketScoreCalculator,
    private val confidenceCalculator: MarketConfidenceCalculator,
    private val reasonBuilder: MarketReasonBuilder,
) : MarketAnalyzer {

    override fun analyze(
        snapshot: MarketSnapshot,
    ): MarketAnalysis {

        val signals = scoreCalculator.createSignals(snapshot)

        val score = scoreCalculator.calculateScore(signals)

        val confidence = confidenceCalculator.calculate(signals)

        val condition = calculateCondition(score)

        val reasons = reasonBuilder.build(
            snapshot = snapshot,
            signals = signals,
        )

        return MarketAnalysis(
            condition = condition,
            score = score,
            confidence = confidence,
            signals = signals,
            reasons = reasons,
        )
    }

    private fun calculateCondition(
        score: Double,
    ): MarketCondition {
        val absoluteScore = kotlin.math.abs(score)

        return when {
            absoluteScore >= 0.75 ->
                MarketCondition.Critical

            absoluteScore >= 0.45 ->
                MarketCondition.Volatile

            absoluteScore >= 0.20 ->
                MarketCondition.Normal

            else ->
                MarketCondition.Calm
        }
    }
}