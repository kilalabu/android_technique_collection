package com.example.android_technique_collection.core.data.repository

import com.example.android_technique_collection.core.datastore.NiaPreferencesDataSource
import com.example.android_technique_collection.core.datastore.sampleUserData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
        niaPreferencesDataSource = NiaPreferencesDataSource()
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
}

