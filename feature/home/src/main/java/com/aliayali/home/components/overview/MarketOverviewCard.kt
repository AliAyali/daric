package com.aliayali.home.components.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.home.model.MarketOverviewCardUiModel

@Composable
fun MarketOverviewCard(
    overview: MarketOverviewCardUiModel,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            MarketOverviewHeader()

            Spacer(Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MarketStatusRing(status = overview.marketStatus)
                Spacer(Modifier.width(10.dp))
                MarketInsight(
                    title = overview.insightTitle,
                    description = overview.insightDescription,
                )
            }

            Spacer(Modifier.height(28.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.08f),
            )

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                MarketPriceCard(
                    modifier = Modifier.weight(1f),
                    title = overview.usd.name,
                    price = overview.usd.formattedTomanPrice,
                    change = overview.usd.formattedChange,
                    isPositive = overview.usd.isPositive,
                )

                MarketPriceCard(
                    modifier = Modifier.weight(1f),
                    title = overview.gold18.name,
                    price = overview.gold18.formattedTomanPrice,
                    change = overview.gold18.formattedChange,
                    isPositive = overview.gold18.isPositive,
                )
            }

        }

    }

}