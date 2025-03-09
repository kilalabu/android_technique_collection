package com.example.android_technique_collection.core.datastore

import com.example.android_technique_collection.core.model.DarkThemeConfig
import com.example.android_technique_collection.core.model.ThemeBrand
import com.example.android_technique_collection.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NiaPreferencesDataSource @Inject constructor(
) {
    // 本来はDataStoreから取得する
    val userData: Flow<UserData> = flow {
        emit(sampleUserData)
    }
}

val sampleUserData = UserData(
    bookmarkedNewsResources = setOf("N1"),
    viewedNewsResources = setOf("N1"),
    followedTopics = setOf("T1", "T2"),
    themeBrand = ThemeBrand.DEFAULT,
    darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    useDynamicColor = false,
    shouldHideOnboarding = true,
)