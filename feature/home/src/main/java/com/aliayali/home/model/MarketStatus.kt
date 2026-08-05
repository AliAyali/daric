package com.aliayali.home.model

enum class MarketStatus(
    val progress: Float,
    val tone: MarketStatusTone,
) {
    Calm(
        progress = .25f,
        tone = MarketStatusTone.Success,
    ),

    Normal(
        progress = .50f,
        tone = MarketStatusTone.Normal,
    ),

    Volatile(
        progress = .80f,
        tone = MarketStatusTone.Warning,
    ),

    Critical(
        progress = 1f,
        tone = MarketStatusTone.Error,
    ),
}

enum class MarketStatusTone {
    Success,
    Normal,
    Warning,
    Error,
}