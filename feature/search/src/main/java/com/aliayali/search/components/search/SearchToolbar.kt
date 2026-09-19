package com.aliayali.search.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.icon.DaricIcons

@Composable
fun SearchToolbar(
    searchFieldValue: TextFieldValue,
    onSearchFieldValueChanged: (TextFieldValue) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl
        ) {
            SearchTextField(
                value = searchFieldValue,
                onValueChange = onSearchFieldValueChanged,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(
            modifier = Modifier.width(10.dp),
        )

        IconButton(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.08f,
                    ),
                    shape = CircleShape,
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