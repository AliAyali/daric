package com.aliayali.model.analysis

data class MarketAnalysis(
    val condition: MarketCondition,
    val score: Double,
    val confidence: Double,
    val signals: List<MarketSignal>,
    val reasons: List<MarketReason>,
)