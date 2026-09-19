package com.aliayali.marketdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.icon.DaricIcons

@Composable
fun MarketDetailTopBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isPersian = title.any { char ->
        char in '\u0600'..'\u06FF'
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isPersian) Arrangement.End else Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )
        if (isPersian) Spacer(Modifier.width(5.dp))
        IconButton(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = .1f),
                    shape = CircleShape
                ),
            onClick = onBackClick,
        ) {
            Icon(
                imageVector = DaricIcons.ArrowForward,
                contentDescription = null,
            )
        }
    }
}