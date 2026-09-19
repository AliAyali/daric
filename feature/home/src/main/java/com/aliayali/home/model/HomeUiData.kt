package com.aliayali.home.model

data class HomeUiData(
    val overview: MarketOverviewCardUiModel,
    val coins: List<CoinUiModel>,
    val marketAssets: List<MarketAssetUiModel>,
)