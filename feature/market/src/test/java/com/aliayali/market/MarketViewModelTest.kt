package com.aliayali.market

import com.aliayali.common.error.AppError
import com.aliayali.common.result.AppResult
import com.aliayali.domain.GetMarketPageDataUseCase
import com.aliayali.domain.ObserveNetworkConnectivityUseCase
import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.domain.repository.NetworkMonitor
import com.aliayali.domain.sync.MarketSyncer
import com.aliayali.market.model.MarketListState
import com.aliayali.market.model.MarketTab
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import com.aliayali.model.market.MarketData
import com.aliayali.testing.rules.TestDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MarketViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val networkState = MutableStateFlow(true)

    private val marketRepository = FakeMarketRepository()

    private val marketAssetRepository = FakeMarketAssetRepository()

    private val marketSyncer = FakeMarketSyncer()

    private fun createViewModel(): MarketViewModel {
        val getMarketPageDataUseCase = GetMarketPageDataUseCase(
            marketRepository = marketRepository,
            marketAssetRepository = marketAssetRepository,
        )

        val observeNetworkConnectivityUseCase =
            ObserveNetworkConnectivityUseCase(
                networkMonitor = FakeNetworkMonitor(networkState),
            )

        return MarketViewModel(
            getMarketPageDataUseCase = getMarketPageDataUseCase,
            marketSyncer = marketSyncer,
            observeNetworkConnectivityUseCase = observeNetworkConnectivityUseCase,
        )
    }

    @Test
    fun `initial state is success`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            assertTrue(
                viewModel.uiState.value is MarketUiState.Success,
            )
        }

    @Test
    fun `initial sync is performed`() =
        runTest {
            createViewModel()

            advanceUntilIdle()

            assertEquals(
                1,
                marketSyncer.syncCallCount,
            )
        }

    @Test
    fun `successful initial sync keeps success state`() =
        runTest {
            marketSyncer.result = AppResult.Success(Unit)

            val viewModel = createViewModel()

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertTrue(state is MarketUiState.Success)

            state as MarketUiState.Success

            assertFalse(state.isRefreshing)
            assertFalse(state.isOffline)
        }

    @Test
    fun `failed sync keeps existing data and marks state offline`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            assertTrue(
                viewModel.uiState.value is MarketUiState.Success,
            )

            marketSyncer.result = AppResult.Failure(
                error = AppError.NoInternet,
            )

            viewModel.onEvent(MarketEvent.Refresh)

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertTrue(state is MarketUiState.Success)

            state as MarketUiState.Success

            assertTrue(state.isOffline)
            assertFalse(state.isRefreshing)
        }

    @Test
    fun `select tab updates selected tab`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            val initialState = viewModel.uiState.value

            assertTrue(initialState is MarketUiState.Success)

            viewModel.onEvent(
                MarketEvent.SelectTab(
                    tab = MarketTab.MARKET_ASSET,
                ),
            )

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertTrue(state is MarketUiState.Success)

            state as MarketUiState.Success

            assertEquals(
                MarketTab.MARKET_ASSET,
                state.selectedTab,
            )
        }

    @Test
    fun `refresh triggers another sync`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            assertEquals(
                1,
                marketSyncer.syncCallCount,
            )

            viewModel.onEvent(MarketEvent.Refresh)

            advanceUntilIdle()

            assertEquals(
                2,
                marketSyncer.syncCallCount,
            )
        }

    @Test
    fun `network going offline marks success state as offline`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            networkState.value = false

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertTrue(state is MarketUiState.Success)

            state as MarketUiState.Success

            assertTrue(state.isOffline)
        }

    @Test
    fun `coming back online triggers refresh`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            assertEquals(
                1,
                marketSyncer.syncCallCount,
            )

            networkState.value = false

            advanceUntilIdle()

            networkState.value = true

            advanceUntilIdle()

            assertEquals(
                2,
                marketSyncer.syncCallCount,
            )
        }

    @Test
    fun `coming back online clears offline state`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            networkState.value = false

            advanceUntilIdle()

            networkState.value = true

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertTrue(state is MarketUiState.Success)

            state as MarketUiState.Success

            assertFalse(state.isOffline)
        }

    @Test
    fun `market data updates crypto and market asset states`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertTrue(state is MarketUiState.Success)

            state as MarketUiState.Success

            assertTrue(
                state.cryptoState is MarketListState.Success,
            )

            assertTrue(
                state.marketAssetState is MarketListState.Success,
            )

            val cryptoState =
                state.cryptoState as MarketListState.Success

            val marketAssetState =
                state.marketAssetState as MarketListState.Success

            assertEquals(
                2,
                cryptoState.items.size,
            )

            assertEquals(
                3,
                marketAssetState.items.size,
            )
        }

    private class FakeNetworkMonitor(
        private val state: Flow<Boolean>,
    ) : NetworkMonitor {

        override val isOnline: Flow<Boolean>
            get() = state
    }

    private class FakeMarketSyncer : MarketSyncer {

        var result: AppResult<Unit> = AppResult.Success(Unit)

        var syncCallCount = 0

        override suspend fun sync(): AppResult<Unit> {
            syncCallCount++
            return result
        }
    }

    private class FakeMarketRepository : MarketRepository {

        private val marketData = MarketData(
            coins = listOf(
                createCoin("bitcoin"),
                createCoin("ethereum"),
            ),
        )

        override fun observeMarketData(): Flow<MarketData> =
            flowOf(marketData)

        override suspend fun getCoin(
            id: String,
        ): Coin? = null

        override suspend fun getCoinPriceHistory(
            id: String,
            days: Int,
        ) = emptyList<com.aliayali.model.market.MarketPricePoint>()

        override suspend fun syncMarketCoins(
            perPage: Int,
            page: Int,
        ): AppResult<Unit> =
            AppResult.Success(Unit)

        override fun observeMarketData(
            id: String,
        ): Flow<Coin?> =
            flowOf(null)

        override suspend fun syncMarketData(): AppResult<Unit> =
            AppResult.Success(Unit)

        override suspend fun searchCoins(
            query: String,
        ): List<Coin> =
            emptyList()
    }

    private class FakeMarketAssetRepository : MarketAssetRepository {

        private val assets = listOf(
            createAsset(
                id = "usd",
                symbol = "USD",
            ),
            createAsset(
                id = "gold18",
                symbol = "IR_GOLD_18K",
            ),
            createAsset(
                id = "gold-ounce",
                symbol = "XAUUSD",
            ),
        )

        override fun observeMarketAssets(): Flow<List<MarketAsset>> =
            flowOf(assets)

        override fun observeMarketAsset(
            id: String,
        ): Flow<MarketAsset?> =
            flowOf(
                assets.firstOrNull { it.id == id },
            )

        override suspend fun syncMarketAssets(): AppResult<Unit> =
            AppResult.Success(Unit)
    }

    companion object {

        private fun createCoin(
            id: String,
        ) = Coin(
            id = id,
            symbol = id.uppercase(),
            name = id.replaceFirstChar { it.uppercase() },
            imageUrl = "",
            price = 50_000.0,
            changePercent24h = 2.0,
        )

        private fun createAsset(
            id: String,
            symbol: String,
        ) = MarketAsset(
            id = id,
            symbol = symbol,
            name = symbol,
            price = 100_000.0,
            changePercent = 2.0,
            unit = "IRT",
        )
    }
}