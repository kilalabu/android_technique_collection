package com.example.android_technique_collection.core.data.repository

import com.example.android_technique_collection.core.model.NewsResource
import com.example.android_technique_collection.core.model.Topic
import com.example.android_technique_collection.core.model.mapToUserNewsResources
import com.example.android_technique_collection.core.testing.repository.TestNewsRepository
import com.example.android_technique_collection.core.testing.repository.TestUserDataRepository
import com.example.android_technique_collection.core.testing.repository.emptyUserData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.Instant
import kotlin.test.assertEquals

class CompositeUserNewsResourceRepositoryTest {

    private val newsRepository = TestNewsRepository()
    private val userDataRepository = TestUserDataRepository()

    private val userNewsResourceRepository = CompositeUserNewsResourceRepository(
        newsRepository, userDataRepository
    )

    @Test
    fun whenNoFilters_allNewsResourcesAreReturned() = runTest {
        // observe start
        val userNewsResources = userNewsResourceRepository.observeAll()

        newsRepository.sendNewsResources(sampleNewsResources)
        val userData = emptyUserData.copy(
            bookmarkedNewsResources = setOf(sampleNewsResources[0].id, sampleNewsResources[2].id),
            followedTopics = setOf(sampleTopic1.id),
        )
        userDataRepository.setUserData(userData)

        assertEquals(
            sampleNewsResources.mapToUserNewsResources(userData),
            userNewsResources.first(),
        )
    }

    @Test
    fun whenFilteredByFollowedTopics_matchingNewsResourcesAreReturned() = runTest {
        // observe start
        val userNewsResource = userNewsResourceRepository.observeAllForFollowedTopics()

        newsRepository.sendNewsResources(sampleNewsResources)
        val userData = emptyUserData.copy(
            followedTopics = setOf(sampleTopic1.id)
        )
        userDataRepository.setUserData(userData)

        assertEquals(
            sampleNewsResources
                .filter { sampleTopic1 in it.topics }
                .mapToUserNewsResources(userData),
            userNewsResource.first()
        )
    }

    companion object {
        private val sampleTopic1 = Topic(
            id = "Topic1",
            name = "Headlines",
            shortDescription = "",
            longDescription = "long description",
            url = "URL",
            imageUrl = "image URL",
        )

        private val sampleTopic2 = Topic(
            id = "Topic2",
            name = "UI",
            shortDescription = "",
            longDescription = "long description",
            url = "URL",
            imageUrl = "image URL",
        )

        private val sampleNewsResources = listOf(
            NewsResource(
                id = "1",
                title = "Thanks for helping us reach 1M YouTube Subscribers",
                content = "Thank you everyone for following the Now in Android series and everything the " +
                        "Android Developers YouTube channel has to offer. During the Android Developer " +
                        "Summit, our YouTube channel reached 1 million subscribers! Here’s a small video to " +
                        "thank you all.",
                url = "https://youtu.be/-fJ6poHQrjM",
                headerImageUrl = "https://i.ytimg.com/vi/-fJ6poHQrjM/maxresdefault.jpg",
                publishDate = Instant.parse("2021-11-09T00:00:00.000Z"),
                type = "Video 📺",
                topics = listOf(sampleTopic1),
            ),
            NewsResource(
                id = "2",
                title = "Transformations and customisations in the Paging Library",
                content = "A demonstration of different operations that can be performed with Paging. " +
                        "Transformations like inserting separators, when to create a new pager, and " +
                        "customisation options for consuming PagingData.",
                url = "https://youtu.be/ZARz0pjm5YM",
                headerImageUrl = "https://i.ytimg.com/vi/ZARz0pjm5YM/maxresdefault.jpg",
                publishDate = Instant.parse("2021-11-01T00:00:00.000Z"),
                type = "Video 📺",
                topics = listOf(sampleTopic1, sampleTopic2),
            ),
            NewsResource(
                id = "3",
                title = "Community tip on Paging",
                content = "Tips for using the Paging library from the developer community",
                url = "https://youtu.be/r5JgIyS3t3s",
                headerImageUrl = "https://i.ytimg.com/vi/r5JgIyS3t3s/maxresdefault.jpg",
                publishDate = Instant.parse("2021-11-08T00:00:00.000Z"),
                type = "Video 📺",
                topics = listOf(sampleTopic2),
            ),
        )
    }
}