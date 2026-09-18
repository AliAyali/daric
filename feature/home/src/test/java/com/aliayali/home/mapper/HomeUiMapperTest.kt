package com.aliayali.home.mapper

import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeUiMapperTest {

    @Test
    fun `coin maps basic fields correctly`() {
        val coin = createCoin(
            id = "bitcoin",
            symbol = "BTC",
            name = "Bitcoin",
            imageUrl = "https://example.com/btc.png",
        )

        val result = coin.asUiModel(dollarToToman = 100_000.0)

        assertEquals("bitcoin", result.id)
        assertEquals("BTC", result.symbol)
        assertEquals("Bitcoin", result.name)
        assertEquals("https://example.com/btc.png", result.imageUrl)
    }

    @Test
    fun `coin calculates toman price using dollar rate`() {
        val coin = createCoin(
            price = 50_000.0,
        )

        val result = coin.asUiModel(
            dollarToToman = 100_000.0,
        )

        assertTrue(result.formattedTomanPrice!!.contains("تومان"))
    }

    @Test
    fun `coin does not create toman price when dollar rate is null`() {
        val coin = createCoin(
            price = 50_000.0,
        )

        val result = coin.asUiModel(
            dollarToToman = null,
        )

        assertEquals(null, result.formattedTomanPrice)
    }

    @Test
    fun `coin uses dash when change is null`() {
        val coin = createCoin(
            changePercent24h = null,
        )

        val result = coin.asUiModel(
            dollarToToman = 100_000.0,
        )

        assertEquals("-", result.formattedChange)
    }

    @Test
    fun `coin is positive when change is positive`() {
        val coin = createCoin(
            changePercent24h = 5.0,
        )

        val result = coin.asUiModel(
            dollarToToman = 100_000.0,
        )

        assertTrue(result.isPositive)
    }

    @Test
    fun `coin is not positive when change is negative`() {
        val coin = createCoin(
            changePercent24h = -5.0,
        )

        val result = coin.asUiModel(
            dollarToToman = 100_000.0,
        )

        assertFalse(result.isPositive)
    }

    @Test
    fun `coin with null price has no formatted prices`() {
        val coin = createCoin(
            price = null,
        )

        val result = coin.asUiModel(
            dollarToToman = 100_000.0,
        )

        assertEquals(null, result.formattedDollarPrice)
        assertEquals(null, result.formattedTomanPrice)
    }

    @Test
    fun `market asset maps basic fields correctly`() {
        val asset = createAsset(
            id = "usd",
            symbol = "USD",
            name = "Dollar",
            unit = "IRT",
            price = 100_000.0,
            changePercent = 2.5,
        )

        val result = asset.asUiModel()

        assertEquals("usd", result.id)
        assertEquals("USD", result.symbol)
        assertEquals("Dollar", result.name)
        result.price?.let { assertEquals(100_000.0, it, 0.0) }
    }

    @Test
    fun `market asset uses dash when price is null`() {
        val asset = createAsset(
            price = null,
        )

        val result = asset.asUiModel()

        assertEquals("-", result.formattedPrice)
    }

    @Test
    fun `market asset uses dash when change is null`() {
        val asset = createAsset(
            changePercent = null,
        )

        val result = asset.asUiModel()

        assertEquals("-", result.formattedChange)
    }

    @Test
    fun `market asset is positive when change is zero`() {
        val asset = createAsset(
            changePercent = 0.0,
        )

        val result = asset.asUiModel()

        assertTrue(result.isPositive)
    }

    @Test
    fun `market asset is not positive when change is negative`() {
        val asset = createAsset(
            changePercent = -2.5,
        )

        val result = asset.asUiModel()

        assertFalse(result.isPositive)
    }

    @Test
    fun `market asset formats positive change with plus sign`() {
        val asset = createAsset(
            changePercent = 2.5,
        )

        val result = asset.asUiModel()

        assertEquals("+2.5%", result.formattedChange)
    }

    @Test
    fun `market asset formats negative change without plus sign`() {
        val asset = createAsset(
            changePercent = -2.5,
        )

        val result = asset.asUiModel()

        assertEquals("-2.5%", result.formattedChange)
    }

    private fun createCoin(
        id: String = "bitcoin",
        symbol: String = "BTC",
        name: String = "Bitcoin",
        imageUrl: String? = null,
        price: Double? = 50_000.0,
        changePercent24h: Double? = 5.0,
    ) = Coin(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = imageUrl.toString(),
        price = price,
        changePercent24h = changePercent24h,
    )

    private fun createAsset(
        id: String = "usd",
        symbol: String = "USD",
        name: String = "Dollar",
        price: Double? = 100_000.0,
        changePercent: Double? = 2.5,
        unit: String = "IRT",
    ) = MarketAsset(
        id = id,
        symbol = symbol,
        name = name,
        price = price,
        changePercent = changePercent,
        unit = unit,
    )
}