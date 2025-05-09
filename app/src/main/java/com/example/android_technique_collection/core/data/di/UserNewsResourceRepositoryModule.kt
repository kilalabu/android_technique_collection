package com.example.android_technique_collection.core.data.di

import com.example.android_technique_collection.core.data.repository.CompositeUserNewsResourceRepository
import com.example.android_technique_collection.core.data.repository.UserNewsResourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UserNewsResourceRepositoryModule {
    /**
     * Repository interfaceと実装が1対1である
     * 今後、TestRepositoryを作って、UserNewsResourceRepositoryを継承させる場合はDataModuleに移行する
     */
    @Binds
    fun bindsUserNewsResourceRepository(
        userDataRepository: CompositeUserNewsResourceRepository,
    ): UserNewsResourceRepository
}