package com.aliayali.home.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.home.R

@Composable
fun MarketOverviewCard(
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

            Header()

            Spacer(Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MarketStatusRing(status = MarketStatus.Volatile)
                Spacer(Modifier.width(10.dp))
                MarketInsight(
                    title = stringResource(R.string.feature_home_market_insight_title),
                    description = stringResource(
                        R.string.feature_home_market_insight_description
                    ),
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
                    title = "دلار آمریکا",
                    price = "85,320 تومان",
                    change = "+1.24%",
                    isPositive = true,
                )

                MarketPriceCard(
                    modifier = Modifier.weight(1f),
                    title = "طلای ۱۸ عیار",
                    price = "7,210,000",
                    change = "-0.31%",
                    isPositive = false,
                )
            }

        }

    }

}