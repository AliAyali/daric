package com.aliayali.market

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun MarketRoute(
    viewModel: MarketViewModel = hiltViewModel(),
) {
    MarketScreen()
}