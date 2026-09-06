package com.aliayali.news.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliayali.news.R
import com.aliayali.news.model.NewsCategory

@Composable
fun NewsCategoryChip(
    category: NewsCategory,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val title = getNewsCategoryTitle(category)

    Text(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp)
            .background(
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            ),
        text = title,
        color = if (selected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
    )
}

@Composable
private fun getNewsCategoryTitle(
    category: NewsCategory,
): String {
    return when (category) {
        NewsCategory.ALL -> stringResource(R.string.feature_news_category_all)
        NewsCategory.CURRENCY -> stringResource(R.string.feature_news_category_currency)
        NewsCategory.GOLD -> stringResource(R.string.feature_news_category_gold)
        NewsCategory.CRYPTO -> stringResource(R.string.feature_news_category_crypto)
        NewsCategory.STOCK -> stringResource(R.string.feature_news_category_stock)
        NewsCategory.ECONOMY -> stringResource(R.string.feature_news_category_economy)
        NewsCategory.OIL -> stringResource(R.string.feature_news_category_oil)
    }
}