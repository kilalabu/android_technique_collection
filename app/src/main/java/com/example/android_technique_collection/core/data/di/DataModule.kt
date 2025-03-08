package com.example.android_technique_collection.core.data.di

import com.example.android_technique_collection.core.data.repository.NewsRepository
import com.example.android_technique_collection.core.data.repository.OfflineFirstNewsRepository
import com.example.android_technique_collection.core.data.repository.OfflineFirstUserDataRepository
import com.example.android_technique_collection.core.data.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsNewsResourceRepository(
        repositoryImpl: OfflineFirstNewsRepository
    ): NewsRepository

    @Binds
    internal abstract fun bindsUserDataRepository(
        repositoryImpl: OfflineFirstUserDataRepository
    ): UserDataRepository
}