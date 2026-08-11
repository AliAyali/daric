package com.aliayali.model.analysis

data class MarketAnalysis(
    val status: MarketStatus,
    val score: Double,
    val title: String,
    val description: String,
)