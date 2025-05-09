package com.example.android_technique_collection.core.data.repository

import com.example.android_technique_collection.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    /**
     * Stream of [UserData]
     */
    val userData: Flow<UserData>

    suspend fun setNewsResourceBookmarked(newsResourceId: String, bookmarked: Boolean)

}