package com.aliayali.home.model

import com.aliayali.model.data.MarketCategory

data class MarketSectionCardUiModel(
    val category: MarketCategory,
    val items: List<CoinUiModel>,
)