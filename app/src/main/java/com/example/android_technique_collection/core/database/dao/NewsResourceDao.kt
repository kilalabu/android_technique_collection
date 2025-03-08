package com.example.android_technique_collection.core.database.dao

import com.example.android_technique_collection.core.database.model.NewsResourceEntity
import com.example.android_technique_collection.core.database.model.PopulatedNewsResource
import com.example.android_technique_collection.core.database.model.TopicEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import javax.inject.Inject

class NewsResourceDao @Inject constructor() {
    // 本来はRoomのDBから取得する
    fun getNewsResources(
        useFilterTopicIds: Boolean = false,
        filterTopicIds: Set<String> = emptySet(),
        useFilterNewsIds: Boolean = false,
        filterNewsIds: Set<String> = emptySet(),
    ): Flow<List<PopulatedNewsResource>> {
        return flow {
            val filteredNews = allNewsResources
                .filter { news ->
                    // ニュースIDでのフィルタリング
                    (!useFilterNewsIds || news.entity.id in filterNewsIds)
                }
                .filter { news ->
                    // トピックIDでのフィルタリング
                    (!useFilterTopicIds || news.topics.any { it.id in filterTopicIds })
                }
                .sortedByDescending { it.entity.publishDate }

            emit(filteredNews)
        }
    }

    companion object {
        private val allNewsResources = listOf(
            PopulatedNewsResource(
                entity = NewsResourceEntity(
                    id = "1",
                    title = "news1",
                    content = "Hilt",
                    url = "url1",
                    headerImageUrl = "headerImageUrl1",
                    type = "Video",
                    publishDate = Instant.parse("2022-12-06T00:00:00.000Z"),
                ),
                topics = listOf(
                    TopicEntity(
                        id = "3",
                        name = "name1",
                        shortDescription = "short description1",
                        longDescription = "long description1",
                        url = "URL1",
                        imageUrl = "image URL1",
                    ),
                ),
            ),
            PopulatedNewsResource(
                entity = NewsResourceEntity(
                    id = "2",
                    title = "news2",
                    content = "Compose",
                    url = "url2",
                    headerImageUrl = "headerImageUrl2",
                    type = "Article",
                    publishDate = Instant.parse("2023-01-15T00:00:00.000Z"),
                ),
                topics = listOf(
                    TopicEntity(
                        id = "4",
                        name = "name2",
                        shortDescription = "short description2",
                        longDescription = "long description2",
                        url = "URL2",
                        imageUrl = "image URL2",
                    ),
                    TopicEntity(
                        id = "3",
                        name = "name1",
                        shortDescription = "short description1",
                        longDescription = "long description1",
                        url = "URL1",
                        imageUrl = "image URL1",
                    ),
                ),
            ),
            PopulatedNewsResource(
                entity = NewsResourceEntity(
                    id = "3",
                    title = "news3",
                    content = "Kotlin",
                    url = "url3",
                    headerImageUrl = "headerImageUrl3",
                    type = "Article",
                    publishDate = Instant.parse("2023-02-20T00:00:00.000Z"),
                ),
                topics = listOf(
                    TopicEntity(
                        id = "5",
                        name = "name3",
                        shortDescription = "short description3",
                        longDescription = "long description3",
                        url = "URL3",
                        imageUrl = "image URL3",
                    ),
                ),
            ),
        )
    }
}