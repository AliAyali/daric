package com.aliayali.marketdetail.components.chart

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.common.util.formattedDollarPrice
import com.aliayali.marketdetail.model.MarketPricePointUiModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.compose.cartesian.data.lineModel
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.data.ExtraStore
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun MarketChartCard(
    points: List<MarketPricePointUiModel>,
    modifier: Modifier = Modifier,
) {
    if (points.isEmpty()) {
        return
    }

    val maxLabelCount = 6

    val modelProducer = remember {
        CartesianChartModelProducer()
    }

    val scrollState = rememberVicoScrollState(
        scrollEnabled = false,
    )

    val tehranZone = remember {
        ZoneOffset.ofHoursMinutes(3, 30)
    }

    val timeFormatter = remember {
        DateTimeFormatter
            .ofPattern("HH:mm")
            .withZone(tehranZone)
    }

    val sortedPoints = remember(points) {
        points.sortedBy { it.timestamp }
    }

    val xValues = remember(sortedPoints) {
        sortedPoints.indices.map { it.toDouble() }
    }

    val labelSpacing = remember(sortedPoints.size) {
        if (sortedPoints.size <= maxLabelCount) {
            1
        } else {
            ceil(
                (sortedPoints.size - 1).toDouble() /
                        (maxLabelCount - 1),
            ).toInt()
        }
    }

    val labelOffset = remember(
        sortedPoints.size,
        labelSpacing,
    ) {
        if (sortedPoints.size <= 1) {
            0
        } else {
            sortedPoints.lastIndex % labelSpacing
        }
    }

    val prices = remember(sortedPoints) {
        sortedPoints.map { it.price.toDouble() }
    }

    val minPrice = prices.minOrNull() ?: 0.0
    val maxPrice = prices.maxOrNull() ?: 0.0

    val priceRange = maxPrice - minPrice

    val yPadding = remember(
        minPrice,
        maxPrice,
    ) {
        if (priceRange > 0.0) {
            priceRange * 0.10
        } else {
            (kotlin.math.abs(minPrice) * 0.01)
                .coerceAtLeast(1.0)
        }
    }

    val chartMinY = minPrice - yPadding
    val chartMaxY = maxPrice + yPadding

    val rangeProvider = remember(
        chartMinY,
        chartMaxY,
    ) {
        object : CartesianLayerRangeProvider {

            override fun getMinX(
                minX: Double,
                maxX: Double,
                extraStore: ExtraStore,
            ): Double = minX

            override fun getMaxX(
                minX: Double,
                maxX: Double,
                extraStore: ExtraStore,
            ): Double = maxX

            override fun getMinY(
                minY: Double,
                maxY: Double,
                extraStore: ExtraStore,
            ): Double = chartMinY

            override fun getMaxY(
                minY: Double,
                maxY: Double,
                extraStore: ExtraStore,
            ): Double = chartMaxY
        }
    }

    LaunchedEffect(sortedPoints) {
        modelProducer.runTransaction {
            lineModel {
                series(
                    x = xValues,
                    y = prices,
                )
            }
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    rangeProvider = rangeProvider,
                ),

                startAxis = VerticalAxis.rememberStart(
                    valueFormatter = { _, value, _ ->
                        value.formattedDollarPrice()
                    },
                ),

                bottomAxis = HorizontalAxis.rememberBottom(
                    itemPlacer = remember(
                        labelSpacing,
                        labelOffset,
                    ) {
                        HorizontalAxis.ItemPlacer.aligned(
                            spacing = { labelSpacing },
                            offset = { labelOffset },
                            shiftExtremeLines = true,
                            addExtremeLabelPadding = true,
                        )
                    },
                    valueFormatter = { _, value, _ ->
                        val index = value
                            .roundToInt()
                            .coerceIn(
                                0,
                                sortedPoints.lastIndex,
                            )

                        timeFormatter.format(
                            Instant.ofEpochMilli(
                                sortedPoints[index].timestamp,
                            ),
                        )
                    },
                ),

                getXStep = { _, _, _ -> 1.0 },
            ),
            modelProducer,
            scrollState = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(16.dp),
        )
    }
}