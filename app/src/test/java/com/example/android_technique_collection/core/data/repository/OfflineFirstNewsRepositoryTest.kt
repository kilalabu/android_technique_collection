package com.example.android_technique_collection.core.data.repository

import com.example.android_technique_collection.core.database.dao.NewsResourceDao
import com.example.android_technique_collection.core.database.model.PopulatedNewsResource
import com.example.android_technique_collection.core.database.model.asExternalModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class OfflineFirstNewsRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var newsResourceDao: NewsResourceDao
    private lateinit var subject: OfflineFirstNewsRepository

    @Before
    fun setup() {
        newsResourceDao =  NewsResourceDao()
        subject = OfflineFirstNewsRepository(
            newsResourceDao = newsResourceDao
        )
    }

    @Test
    fun offlineFirstNewsRepository_news_resources_stream() {
        testScope.runTest {
            assertEquals(
                newsResourceDao.getNewsResources()
                    .first()
                    .map(PopulatedNewsResource::asExternalModel),
                subject.getNewsResources(NewsResourceQuery())
                    .first()
            )
        }
    }

    @Test
    fun offlineFirstNewsRepository_news_resources_stream_filters_by_topic_id() {
        val filterTopicIds = setOf("T1")
        testScope.runTest {
            assertEquals(
                newsResourceDao.getNewsResources(
                    useFilterTopicIds = true, filterTopicIds = filterTopicIds
                )
                    .first()
                    .map(PopulatedNewsResource::asExternalModel),
                subject.getNewsResources(NewsResourceQuery(filterTopicIds = filterTopicIds))
                    .first()
            )
        }
    }
}