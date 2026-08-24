package com.aliayali.marketdetail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.marketdetail.model.MarketPricePointUiModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.lineModel
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart

@Composable
fun MarketChartCard(
    points: List<MarketPricePointUiModel>,
    modifier: Modifier = Modifier,
) {
    val modelProducer = remember {
        CartesianChartModelProducer()
    }

    LaunchedEffect(points) {
        modelProducer.runTransaction {
            lineModel {
                series(
                    x = points.map { it.timestamp },
                    y = points.map { it.price },
                )
            }
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis = VerticalAxis.rememberStart(),
                bottomAxis = HorizontalAxis.rememberBottom(),
            ),
            modelProducer = modelProducer,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
        )
    }
}