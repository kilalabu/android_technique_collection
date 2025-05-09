package com.example.android_technique_collection.core.model

/**
 * A [topic] with the additional information for whether or not it is followed.
 */
// TODO consider changing to UserTopic and flattening
data class FollowableTopic(
    val topic: Topic,
    val isFollowed: Boolean,
)