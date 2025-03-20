package com.example.android_technique_collection.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.android_technique_collection.UserPreferences
import com.example.android_technique_collection.core.datastore.UserPreferencesSerializer
import com.example.android_technique_collection.di.ApplicationScope
import com.example.android_technique_collection.di.Dispatcher
import com.example.android_technique_collection.di.NiaDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(NiaDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        userPreferencesSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            /**
             * - `scope`は`@ApplicationScope`で提供されており、`SupervisorJob() + Dispatchers.Default`を持つが、
             * - `scope.coroutineContext + ioDispatcher` によって、`Dispatchers.Default`は`Dispatchers.IO`に置き換えられる。
             * - `SupervisorJob()` はそのまま維持されるため、子コルーチンが失敗しても他の処理に影響を与えない。
             */
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
}