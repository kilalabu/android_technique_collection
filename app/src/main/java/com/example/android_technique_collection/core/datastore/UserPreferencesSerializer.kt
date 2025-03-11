package com.example.android_technique_collection.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.android_technique_collection.UserPreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

// データ型の読み取り / 書き込みの方法を DataStore に指示するためのSerializer
// 公式の通りに実装 https://developer.android.com/topic/libraries/architecture/datastore
class UserPreferencesSerializer @Inject constructor() : Serializer<UserPreferences>{
    override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences =
        try {
            UserPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}