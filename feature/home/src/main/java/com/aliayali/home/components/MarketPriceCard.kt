package com.aliayali.home.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.icon.DaricIcons.ArrowDown
import com.aliayali.designsystem.icon.DaricIcons.ArrowUp


@Composable
fun MarketPriceCard(
    title: String,
    price: String,
    change: String,
    isPositive: Boolean,
    modifier: Modifier = Modifier,
) {
    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        visible = true
    }
    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else 20.dp,
        animationSpec = tween(700),
        label = "priceOffset"
    )
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background.copy(alpha = .06f),
                shape = RoundedCornerShape(20.dp),
            )
            .padding(16.dp)
            .offset(y = offsetY),
        horizontalAlignment = Alignment.End
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.background.copy(alpha = .65f),
        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        Text(
            text = price,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.background,
        )


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            Icon(
                imageVector =
                    if (isPositive) ArrowUp
                    else ArrowDown,

                contentDescription = null,

                tint =
                    if (isPositive)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error
            )


            Text(
                text = change,
                color =
                    if (isPositive)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error,

                style = MaterialTheme.typography.labelLarge,
            )

        }

    }

}