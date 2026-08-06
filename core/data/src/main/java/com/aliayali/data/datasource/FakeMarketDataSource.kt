package com.aliayali.data.datasource

import com.aliayali.model.data.Coin
import com.aliayali.model.data.MarketCategory
import com.aliayali.model.data.MarketOverview
import com.aliayali.model.data.MarketSection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMarketDataSource @Inject constructor() {
    fun getOverview(): MarketOverview = MarketOverview(
        insightTitle = "بازار امروز آرام و خوب است",
        insightDescription = "دلار رشد ملایمی داشته و طلا تغییر محسوسی نداشته است",

        usd = Coin(
            id = "usd",
            symbol = "USD",
            name = "دلار آمریکا",
            dollarPrice = 1.0,
            tomanPrice = 86000,
            changePercent = 1.24,
        ),

        gold18 = Coin(
            id = "gold18",
            symbol = "XAU",
            name = "طلای ۱۸ عیار",
            dollarPrice = 41.0,
            tomanPrice = 18603080,
            changePercent = -0.31,
        )
    )

    fun getSections(): List<MarketSection> = listOf(

        MarketSection(
            category = MarketCategory.Crypto,
            items = listOf(
                Coin(
                    id = "btc",
                    symbol = "BTC",
                    name = "Bitcoin",
                    dollarPrice = 21488.0,
                    tomanPrice = 3850000000,
                    changePercent = 2.45
                )
            )
        ),

        MarketSection(
            category = MarketCategory.Currency,
            items = listOf(
                Coin(
                    id = "eur",
                    symbol = "EUR",
                    name = "یورو",
                    dollarPrice = 1.2,
                    tomanPrice = 95000,
                    changePercent = -0.4
                )
            )
        )
    )
}