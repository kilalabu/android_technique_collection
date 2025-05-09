package com.example.android_technique_collection.feature.foryou

import com.example.android_technique_collection.core.model.UserNewsResource

/**
 * A sealed hierarchy describing the state of the feed of news resources.
 */
sealed interface NewsFeedUiState {
    /**
     * The feed is still loading.
     */
    data object Loading : NewsFeedUiState

    /**
     * The feed is loaded with the given list of news resources.
     */
    data class Success(
        /**
         * The list of news resources contained in this feed.
         */
        val feed: List<UserNewsResource>,
    ) : NewsFeedUiState
}