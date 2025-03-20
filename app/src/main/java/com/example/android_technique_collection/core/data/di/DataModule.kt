package com.example.android_technique_collection.core.data.di

import com.example.android_technique_collection.core.data.repository.NewsRepository
import com.example.android_technique_collection.core.data.repository.OfflineFirstNewsRepository
import com.example.android_technique_collection.core.data.repository.OfflineFirstUserDataRepository
import com.example.android_technique_collection.core.data.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Repository interfaceと実装が1対多の場合
 */
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

/**
 * `abstract class` にすることで、テスト時には `TestDataModule` を使って
 * テスト用のリポジトリに置き換えることができる。
 *
 * @Module
 * @TestInstallIn(
 *     components = [SingletonComponent::class],
 *     replaces = [DataModule::class],
 * )
 * internal interface TestDataModule {
 *     @Binds
 *     fun bindsNewsResourceRepository(
 *         fakeNewsRepository: FakeNewsRepository,
 *     ): NewsRepository
 *
 *     @Binds
 *     fun bindsUserDataRepository(
 *         userDataRepository: FakeUserDataRepository,
 *     ): UserDataRepository
 * }
 */