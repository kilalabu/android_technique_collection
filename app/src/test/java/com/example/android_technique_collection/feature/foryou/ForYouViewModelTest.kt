package com.example.android_technique_collection.feature.foryou

import com.example.android_technique_collection.MainDispatcherRule
import com.example.android_technique_collection.core.data.repository.CompositeUserNewsResourceRepository
import com.example.android_technique_collection.core.model.NewsResource
import com.example.android_technique_collection.core.model.Topic
import com.example.android_technique_collection.core.model.mapToUserNewsResources
import com.example.android_technique_collection.core.testing.repository.TestNewsRepository
import com.example.android_technique_collection.core.testing.repository.TestUserDataRepository
import com.example.android_technique_collection.core.testing.repository.emptyUserData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.collect
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import kotlin.test.assertEquals

class ForYouViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userDataRepository = TestUserDataRepository()
    private val newsRepository = TestNewsRepository()
    private val userNewsResourceRepository = CompositeUserNewsResourceRepository(
        newsRepository = newsRepository,
        userDataRepository = userDataRepository
    )

    private lateinit var viewModel: ForYouViewModel

    @Before
    fun setup() {
        viewModel = ForYouViewModel(userNewsResourceRepository)
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertEquals(
            expected = NewsFeedUiState.Loading,
            actual = viewModel.feedState.value
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun newsResourcesAreLoadedSuccessfully() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.feedState.collect() }

        val followedTopicIds = setOf("0", "1")
        val userData = emptyUserData.copy(followedTopics = followedTopicIds)
        userDataRepository.setUserData(userData)
        newsRepository.sendNewsResources(sampleNewsResources)

        assertEquals(
            expected = NewsFeedUiState.Success(
                feed = sampleNewsResources.mapToUserNewsResources(userData)
            ),
            actual = viewModel.feedState.value
        )
    }


    companion object {
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
                topics = listOf(
                    Topic(
                        id = "0",
                        name = "Headlines",
                        shortDescription = "",
                        longDescription = "long description",
                        url = "URL",
                        imageUrl = "image URL",
                    ),
                ),
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
                topics = listOf(
                    Topic(
                        id = "1",
                        name = "UI",
                        shortDescription = "",
                        longDescription = "long description",
                        url = "URL",
                        imageUrl = "image URL",
                    ),
                ),
            ),
            NewsResource(
                id = "3",
                title = "Community tip on Paging",
                content = "Tips for using the Paging library from the developer community",
                url = "https://youtu.be/r5JgIyS3t3s",
                headerImageUrl = "https://i.ytimg.com/vi/r5JgIyS3t3s/maxresdefault.jpg",
                publishDate = Instant.parse("2021-11-08T00:00:00.000Z"),
                type = "Video 📺",
                topics = listOf(
                    Topic(
                        id = "1",
                        name = "UI",
                        shortDescription = "",
                        longDescription = "long description",
                        url = "URL",
                        imageUrl = "image URL",
                    ),
                ),
            ),
        )

    }
}