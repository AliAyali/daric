package com.aliayali.news.mapper

import com.aliayali.model.news.News
import com.aliayali.news.model.NewsUiModel

internal fun News.asUiModel(): NewsUiModel =
    NewsUiModel(
        id = id,
        sourceName = sourceName,
        title = title,
        description = description,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        url = url,
    )