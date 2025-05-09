package com.example.android_technique_collection.core.database.model

import com.example.android_technique_collection.core.model.Topic

data class TopicEntity(
    val id: String,
    val name: String,
    val shortDescription: String,
    val longDescription: String,
    val url: String,
    val imageUrl: String,
)

fun TopicEntity.asExternalModel() = Topic(
    id = id,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    url = url,
    imageUrl = imageUrl,
)