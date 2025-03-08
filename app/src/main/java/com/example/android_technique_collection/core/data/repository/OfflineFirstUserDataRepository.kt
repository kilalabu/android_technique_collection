package com.example.android_technique_collection.core.data.repository

import com.example.android_technique_collection.core.datastore.NiaPreferencesDataSource
import com.example.android_technique_collection.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class OfflineFirstUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: NiaPreferencesDataSource,
) : UserDataRepository {

    override val userData: Flow<UserData> = niaPreferencesDataSource.userData
}