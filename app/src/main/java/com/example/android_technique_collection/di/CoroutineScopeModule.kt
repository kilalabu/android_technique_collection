package com.example.android_technique_collection.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME) // ランタイムまで保持される。アノテーション(@Qualifier)を使う時には必須
@Qualifier
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineScopesModule {

    @Provides
    @Singleton
    @ApplicationScope
    fun providesCoroutineScope(
        @Dispatcher(NiaDispatchers.Default) dispatcher: CoroutineDispatcher,
    ): CoroutineScope {
        /**
         * - `SupervisorJob()` を使用することで、1つの子 Coroutine が失敗しても他の子 Coroutine に影響しない。
         * - `dispatcher`をDIで提供することで、テスト時に`TestCoroutineDispatcher` に置き換えることができる。
         */
       return CoroutineScope(SupervisorJob() + dispatcher)
    }

    /**
     * `dispatcher` を DI で提供することで、テスト時に `TestCoroutineScopesModule` を使い、
     * `CoroutineScope` の `Dispatcher` を `TestDispatcher` に置き換えることができる。
     *
     * @Module
     * @TestInstallIn(components = [SingletonComponent::class], replaces = [CoroutineScopesModule::class])
     * object TestCoroutineScopesModule {
     *     @Provides
     *     @Singleton
     *     @ApplicationScope
     *     fun providesTestCoroutineScope(): CoroutineScope = TestScope()
     * }
     */
}
