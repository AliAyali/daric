package com.aliayali.news.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.aliayali.designsystem.component.ShimmerBox
import com.aliayali.news.R
import com.aliayali.news.model.NewsUiModel

@Composable
fun NewsItem(
    news: NewsUiModel,
    modifier: Modifier = Modifier,
) {
    var isExpanded by rememberSaveable(news.id) {
        mutableStateOf(false)
    }

    val uriHandler = LocalUriHandler.current

    Row(
        modifier = modifier
            .clickable {
                isExpanded = !isExpanded
            }
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        horizontalArrangement = if (isExpanded) {
            Arrangement.Start
        } else {
            Arrangement.spacedBy(12.dp)
        },
        verticalAlignment = Alignment.Top,
    ) {
        if (!isExpanded) {
            NewsImage(
                imageUrl = news.imageUrl,
                contentDescription = news.title,
                modifier = Modifier.size(110.dp),
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = news.sourceName,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
            )

            Spacer(
                modifier = Modifier.height(6.dp),
            )

            Text(
                text = news.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                maxLines = if (isExpanded) {
                    Int.MAX_VALUE
                } else {
                    2
                },
                overflow = if (isExpanded) {
                    TextOverflow.Clip
                } else {
                    TextOverflow.Ellipsis
                },
            )

            news.description
                ?.takeIf { it.isNotBlank() }
                ?.let { description ->

                    Spacer(
                        modifier = Modifier.height(6.dp),
                    )

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = if (isExpanded) {
                            Int.MAX_VALUE
                        } else {
                            2
                        },
                        overflow = if (isExpanded) {
                            TextOverflow.Clip
                        } else {
                            TextOverflow.Ellipsis
                        },
                    )
                }

            Spacer(
                modifier = Modifier.height(8.dp),
            )

            Text(
                text = news.publishedAt,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            if (isExpanded) {
                Spacer(
                    modifier = Modifier.height(10.dp),
                )

                Row(
                    modifier = Modifier
                        .clickable {
                            uriHandler.openUri(news.url)
                        }
                        .padding(
                            vertical = 4.dp,
                        ),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp),
                    )

                    Text(
                        text = stringResource(
                            R.string.feature_news_read_full,
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}

@Composable
private fun NewsImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (imageUrl.isNullOrBlank()) {
            NewsImagePlaceholder()
        } else {
            var isLoading by remember { mutableStateOf(true) }
            var hasError by remember { mutableStateOf(false) }

            if (isLoading && !hasError) {
                ShimmerBox(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = .15f),
                            shape = RoundedCornerShape(28.dp)
                        ),
                    shape = shape,
                )
            }

            if (hasError) {
                NewsImagePlaceholder()
            } else {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(shape),
                    contentScale = ContentScale.Crop,
                    onState = { state ->
                        when (state) {
                            is AsyncImagePainter.State.Loading,
                            is AsyncImagePainter.State.Empty,
                                -> {
                                isLoading = true
                                hasError = false
                            }

                            is AsyncImagePainter.State.Success -> {
                                isLoading = false
                                hasError = false
                            }

                            is AsyncImagePainter.State.Error -> {
                                isLoading = false
                                hasError = true
                            }
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun NewsImagePlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.Article,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(32.dp),
        )
    }
}