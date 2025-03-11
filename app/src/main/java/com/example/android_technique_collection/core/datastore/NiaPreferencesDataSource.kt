package com.example.android_technique_collection.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.android_technique_collection.DarkThemeConfigProto
import com.example.android_technique_collection.ThemeBrandProto
import com.example.android_technique_collection.UserPreferences
import com.example.android_technique_collection.copy
import com.example.android_technique_collection.core.model.DarkThemeConfig
import com.example.android_technique_collection.core.model.ThemeBrand
import com.example.android_technique_collection.core.model.UserData
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class NiaPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data
        .map {
            UserData(
                bookmarkedNewsResources = it.bookmarkedNewsResourceIdsMap.keys,
                viewedNewsResources = it.viewedNewsResourceIdsMap.keys,
                followedTopics = it.followedTopicIdsMap.keys,
                themeBrand = when (it.themeBrand) {
                    null,
                    ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                    ThemeBrandProto.UNRECOGNIZED,
                    ThemeBrandProto.THEME_BRAND_DEFAULT,
                        -> ThemeBrand.DEFAULT

                    ThemeBrandProto.THEME_BRAND_ANDROID -> ThemeBrand.ANDROID
                },
                darkThemeConfig = when (it.darkThemeConfig) {
                    null,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                    DarkThemeConfigProto.UNRECOGNIZED,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                        ->
                        DarkThemeConfig.FOLLOW_SYSTEM

                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                        DarkThemeConfig.LIGHT

                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                },
                useDynamicColor = it.useDynamicColor,
                shouldHideOnboarding = it.shouldHideOnboarding,
            )
        }

    suspend fun setNewsResourceBookmarked(newsResourceId: String, bookmarked: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    if (bookmarked) {
                        bookmarkedNewsResourceIds.put(newsResourceId, true)
                    } else {
                        bookmarkedNewsResourceIds.remove(newsResourceId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("Preferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun insertSampleUserData() {
        try {
            userPreferences.updateData {
                it.copy {
                    followedTopicIds.putAll(sampleUserData.followedTopics.associateWith { true })
                    viewedNewsResourceIds.putAll(sampleUserData.viewedNewsResources.associateWith { true })
                }
            }
        } catch (ioException: IOException) {
            Log.e("@@@Preferences", "Failed to update user preferences", ioException)
        }
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