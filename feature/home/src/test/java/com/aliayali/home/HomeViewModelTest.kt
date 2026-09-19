package com.aliayali.home

import com.aliayali.common.error.AppError
import com.aliayali.common.result.AppResult
import com.aliayali.domain.GetHomeMarketDataUseCase
import com.aliayali.domain.GetMarketOverviewUseCase
import com.aliayali.domain.MarketAnalyzer
import com.aliayali.domain.ObserveNetworkConnectivityUseCase
import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.domain.repository.NetworkMonitor
import com.aliayali.domain.sync.MarketSyncer
import com.aliayali.model.analysis.MarketAnalysis
import com.aliayali.model.analysis.MarketCondition
import com.aliayali.model.analysis.MarketSignal
import com.aliayali.model.analysis.MarketSignalType
import com.aliayali.model.analysis.MarketSnapshot
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import com.aliayali.model.market.MarketData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var networkState: MutableStateFlow<Boolean>
    private lateinit var marketRepository: FakeMarketRepository
    private lateinit var marketAssetRepository: FakeMarketAssetRepository
    private lateinit var marketSyncer: FakeMarketSyncer

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        networkState = MutableStateFlow(true)
        marketRepository = FakeMarketRepository()
        marketAssetRepository = FakeMarketAssetRepository()
        marketSyncer = FakeMarketSyncer()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): HomeViewModel {
        val networkMonitor = FakeNetworkMonitor(
            state = networkState,
        )

        val marketAnalyzer = object : MarketAnalyzer {

            override fun analyze(
                snapshot: MarketSnapshot,
            ): MarketAnalysis {
                return MarketAnalysis(
                    condition = MarketCondition.Normal,
                    score = 0.5,
                    confidence = 0.8,
                    signals = listOf(
                        MarketSignal(
                            type = MarketSignalType.USD,
                            score = 0.5,
                            weight = 0.15,
                        ),
                    ),
                    reasons = emptyList(),
                )
            }
        }

        val getMarketOverviewUseCase = GetMarketOverviewUseCase(
            marketAnalyzer = marketAnalyzer,
        )

        val getHomeMarketDataUseCase = GetHomeMarketDataUseCase(
            marketRepository = marketRepository,
            marketAssetRepository = marketAssetRepository,
            getMarketOverviewUseCase = getMarketOverviewUseCase,
        )

        val observeNetworkConnectivityUseCase =
            ObserveNetworkConnectivityUseCase(
                networkMonitor = networkMonitor,
            )

        return HomeViewModel(
            getHomeMarketDataUseCase = getHomeMarketDataUseCase,
            observeNetworkConnectivityUseCase = observeNetworkConnectivityUseCase,
            marketSyncer = marketSyncer,
        )
    }

    @Test
    fun `initial state becomes success when usable data is available`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            assertTrue(
                viewModel.uiState.value is HomeUiState.Success,
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

            assertTrue(state is HomeUiState.Success)

            state as HomeUiState.Success

            assertFalse(state.isRefreshing)
            assertFalse(state.isOffline)
        }

    @Test
    fun `failed initial sync shows error when there is no local data`() =
        runTest {
            marketRepository.marketData = MarketData(
                coins = emptyList(),
            )

            marketAssetRepository.assets = emptyList()

            marketSyncer.result = AppResult.Failure(
                error = AppError.NoInternet,
            )

            val viewModel = createViewModel()

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertTrue(state is HomeUiState.Error)

            state as HomeUiState.Error

            assertEquals(
                AppError.NoInternet,
                state.error,
            )
        }

    @Test
    fun `failed sync keeps existing data and marks state offline`() =
        runTest {
            // First sync succeeds so local data reaches Success state.
            marketSyncer.result = AppResult.Success(Unit)

            val viewModel = createViewModel()

            advanceUntilIdle()

            assertTrue(
                viewModel.uiState.value is HomeUiState.Success,
            )

            // Now make the next sync fail.
            marketSyncer.result = AppResult.Failure(
                error = AppError.NoInternet,
            )

            viewModel.onEvent(HomeEvent.Refresh)

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertTrue(state is HomeUiState.Success)

            state as HomeUiState.Success

            assertTrue(state.isOffline)
            assertFalse(state.isRefreshing)
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

            viewModel.onEvent(HomeEvent.Refresh)

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

            assertTrue(state is HomeUiState.Success)

            state as HomeUiState.Success

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

            assertTrue(state is HomeUiState.Success)

            state as HomeUiState.Success

            assertFalse(state.isOffline)
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

        var marketData = MarketData(
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
        ): AppResult<Unit> = AppResult.Success(Unit)

        override fun observeMarketData(
            id: String,
        ): Flow<Coin?> = flowOf(null)

        override suspend fun syncMarketData(): AppResult<Unit> =
            AppResult.Success(Unit)

        override suspend fun searchCoins(
            query: String,
        ): List<Coin> = emptyList()
    }

    private class FakeMarketAssetRepository : MarketAssetRepository {

        var assets = listOf(
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