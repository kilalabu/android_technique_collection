package com.example.android_technique_collection.core.data.repository

import com.example.android_technique_collection.core.database.dao.NewsResourceDao
import com.example.android_technique_collection.core.database.model.PopulatedNewsResource
import com.example.android_technique_collection.core.database.model.asExternalModel
import com.example.android_technique_collection.core.model.NewsResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OfflineFirstNewsRepository @Inject constructor(
    private val newsResourceDao: NewsResourceDao,
) : NewsRepository {

    override fun getNewsResources(query: NewsResourceQuery): Flow<List<NewsResource>> {
        return newsResourceDao.getNewsResources(
            useFilterTopicIds = query.filterTopicIds != null,
            filterTopicIds = query.filterTopicIds ?: emptySet(),
            useFilterNewsIds = query.filterNewsIds != null,
            filterNewsIds = query.filterNewsIds ?: emptySet(),
        )
            .map { it.map(PopulatedNewsResource::asExternalModel) }
    }
}