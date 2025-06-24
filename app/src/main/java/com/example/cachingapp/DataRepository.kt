package com.example.cachingapp

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.IOException

/**
 * Repository class responsible for fetching data from network or cache.
 * It implements the layered caching strategy: Memory -> Disk -> Network.
 *
 * @param context The application context, used to initialize HttpClientProvider.
 */
class DataRepository(private val context: Context) {

    private val memoryCacheManager = MemoryCacheManager
    private val httpClient = HttpClientProvider.getOkHttpClient(context)

    /**
     * Sealed class to represent the result of a data fetching operation,
     * including the source of the data.
     */
    sealed class FetchResult {
        data class Success(val data: String, val sourceMessage: String) : FetchResult()
        data class Error(val errorMessage: String) : FetchResult()
    }

    /**
     * Fetches JSON data from the given URL, applying the caching strategy.
     *
     * @param url The URL to fetch data from.
     * @return A [FetchResult] indicating success (with data and source) or failure.
     */
    suspend fun fetchJsonData(url: String): FetchResult {
        // [Step 1] Check Memory Cache
        memoryCacheManager.get(url)?.let { jsonData ->
            return FetchResult.Success(jsonData, "✅ Memory Cache Hit for $url")
        }

        // Operations involving OkHttp (disk cache and network) should be on Dispatchers.IO
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).build()
                val response = httpClient.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string() ?: ""
                    val sourceMessage: String

                    // [Step 2] Check Disk Cache (implicitly handled by OkHttp)
                    // OkHttp's Cache-Control headers determine if it's a disk hit or network fetch.
                    // We can check `response.networkResponse()` and `response.cacheResponse()`.
                    // A null `networkResponse` means it was served from cache.
                    if (response.networkResponse == null && response.cacheResponse != null) {
                        sourceMessage = "✅ Disk Cache Hit for $url"
                    } else {
                        // [Step 3] Fetch from Network
                        sourceMessage = "✅ Fetched from Network: $url"
                    }
                    // Store in memory cache for future access
                    memoryCacheManager.put(url, responseBody)
                    FetchResult.Success(responseBody, sourceMessage)
                } else {
                    FetchResult.Error("❌ Network Error: ${response.code} for $url")
                }
            } catch (e: IOException) {
                FetchResult.Error("❌ Network Error (IOException): ${e.message} for $url")
            }
        }
    }

    /**
     * Clears all caches: custom memory cache, OkHttp disk cache, and Coil caches.
     *
     * @return A log message indicating the completion of the cache clearing process.
     */
    suspend fun clearAllCaches(): String {
        // Clear custom memory cache
        memoryCacheManager.evictAll()

        // Clear OkHttp disk cache
        // This needs to be on an I/O dispatcher if the cache operations are blocking.
        // OkHttp's cache.evictAll() can perform I/O.
        withContext(Dispatchers.IO) {
            HttpClientProvider.clearOkHttpCache()
        }

        // Clear Coil's memory and disk caches
        // Coil's clear methods are safe to call from the main thread,
        // but disk cache clearing might do I/O. For safety, run on IO dispatcher.
        withContext(Dispatchers.IO) {
            HttpClientProvider.clearCoilCache(context) // Pass context if needed by underlying Coil clear
        }

        return "🧹 All caches cleared"
    }
}
