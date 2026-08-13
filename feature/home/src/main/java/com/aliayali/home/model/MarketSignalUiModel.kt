package com.aliayali.home.model

import androidx.annotation.StringRes

data class MarketSignalUiModel(
    @param:StringRes val titleRes: Int,
    val score: Double,
    val weight: Double,
)