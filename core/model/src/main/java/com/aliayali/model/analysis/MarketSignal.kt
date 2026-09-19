package com.aliayali.model.analysis

data class MarketSignal(
    val type: MarketSignalType,
    val score: Double,
    val weight: Double,
)
