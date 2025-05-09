package com.example.android_technique_collection.core.database.model

import com.example.android_technique_collection.core.model.NewsResource
import java.time.Instant

data class NewsResourceEntity(
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String?,
    val publishDate: Instant,
    val type: String,
)

fun NewsResourceEntity.asExternalModel() = NewsResource(
    id = id,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
    topics = listOf(),
)