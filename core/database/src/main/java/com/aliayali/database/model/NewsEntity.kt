package com.aliayali.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliayali.model.news.News

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val sourceId: String?,
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String,
    val content: String?,
)

fun NewsEntity.asModel(): News =
    News(
        id = id,
        sourceId = sourceId,
        sourceName = sourceName,
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        content = content,
    )

fun News.asEntity(): NewsEntity =
    NewsEntity(
        id = id,
        sourceId = sourceId,
        sourceName = sourceName,
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        content = content,
    )