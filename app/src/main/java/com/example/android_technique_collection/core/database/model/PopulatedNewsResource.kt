package com.example.android_technique_collection.core.database.model

import com.example.android_technique_collection.core.model.NewsResource

data class PopulatedNewsResource(
    val entity: NewsResourceEntity,
    val topics: List<TopicEntity>,
)

fun PopulatedNewsResource.asExternalModel() = NewsResource(
    id = entity.id,
    title = entity.title,
    content = entity.content,
    url = entity.url,
    headerImageUrl = entity.headerImageUrl,
    publishDate = entity.publishDate,
    type = entity.type,
    topics = topics.map(TopicEntity::asExternalModel),
)