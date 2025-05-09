package com.example.android_technique_collection.core.testing.repository

import com.example.android_technique_collection.core.data.repository.UserDataRepository
import com.example.android_technique_collection.core.model.DarkThemeConfig
import com.example.android_technique_collection.core.model.ThemeBrand
import com.example.android_technique_collection.core.model.UserData
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

val emptyUserData = UserData(
    bookmarkedNewsResources = emptySet(),
    viewedNewsResources = emptySet(),
    followedTopics = emptySet(),
    themeBrand = ThemeBrand.DEFAULT,
    darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    useDynamicColor = false,
    shouldHideOnboarding = false,
)

class TestUserDataRepository : UserDataRepository {

    private val _userData = MutableSharedFlow<UserData>(
        replay = 1,
        onBufferOverflow = DROP_OLDEST
    )

    /**
     * A test-only API to allow setting of user data directly.
     */
    fun setUserData(userData: UserData) {
        _userData.tryEmit(userData)
    }

    override val userData: Flow<UserData> = _userData.filterNotNull()

    override suspend fun setNewsResourceBookmarked(newsResourceId: String, bookmarked: Boolean) {
        TODO("Not yet implemented")
    }
}