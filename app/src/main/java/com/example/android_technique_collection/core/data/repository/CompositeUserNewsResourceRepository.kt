package com.example.android_technique_collection.core.data.repository

import com.example.android_technique_collection.core.model.UserNewsResource
import com.example.android_technique_collection.core.model.mapToUserNewsResources
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CompositeUserNewsResourceRepository @Inject constructor(
    private val newsRepository: NewsRepository,
    private val userDataRepository: UserDataRepository,
) : UserNewsResourceRepository {

    override fun observeAll(query: NewsResourceQuery): Flow<List<UserNewsResource>> {
        return newsRepository.getNewsResources(query)
            .combine(userDataRepository.userData) { newsResources, userData ->
                // NewsResourceからUserNewsResourceに変換する
                newsResources.mapToUserNewsResources(userData)
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeAllForFollowedTopics(): Flow<List<UserNewsResource>> {
        return userDataRepository.userData.map { it.followedTopics }.distinctUntilChanged()
            .flatMapLatest { followedTopics ->
                when {
                    followedTopics.isEmpty() -> flowOf(emptyList())
                    else -> {
                        observeAll(NewsResourceQuery(filterTopicIds = followedTopics))
                    }
                }
            }
    }

}