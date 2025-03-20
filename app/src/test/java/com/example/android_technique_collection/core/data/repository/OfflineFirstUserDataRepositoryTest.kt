package com.example.android_technique_collection.core.data.repository

import com.example.android_technique_collection.UserPreferences
import com.example.android_technique_collection.core.datastore.NiaPreferencesDataSource
import com.example.android_technique_collection.core.datastore.sampleUserData
import com.example.android_technique_collection.core.testing.datastore.InMemoryDataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class OfflineFirstUserDataRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var niaPreferencesDataSource: NiaPreferencesDataSource
    private lateinit var subject: OfflineFirstUserDataRepository

    @Before
    fun setup() {
        niaPreferencesDataSource = NiaPreferencesDataSource(
            InMemoryDataStore(UserPreferences.getDefaultInstance())
        )
        subject = OfflineFirstUserDataRepository(
            niaPreferencesDataSource = niaPreferencesDataSource
        )
    }

    @Test
    fun offlineFirstUserDataRepository_default_user_data_is_correct() {
        testScope.launch {
            assertEquals(
                sampleUserData,
                subject.userData.first()
            )
        }
    }

    @Test
    fun offlineFirstUserDataRepository_bookmark_news_resource_logic_delegates_to_nia_preferences() {
        testScope.runTest {
            subject.setNewsResourceBookmarked(newsResourceId = "0", bookmarked = true)

            assertEquals(
                setOf("0"),
                subject.userData
                    .map { it.bookmarkedNewsResources }
                    .first()
            )

            subject.setNewsResourceBookmarked(newsResourceId = "1", bookmarked = true)

            assertEquals(
                setOf("0", "1"),
                subject.userData
                    .map { it.bookmarkedNewsResources }
                    .first()
            )

            assertEquals(
                niaPreferencesDataSource.userData
                    .map { it.bookmarkedNewsResources }
                    .first(),
                subject.userData
                    .map { it.bookmarkedNewsResources }
                    .first()
            )
        }
    }
}

