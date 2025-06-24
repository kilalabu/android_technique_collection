package com.example.cachingapp

import android.content.Context
import coil.ImageLoader
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

/**
 * A singleton object responsible for providing instances of OkHttpClient (with disk caching)
 * and Coil's ImageLoader.
 */
object HttpClientProvider {

    private const val OKHTTP_CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB
    private var okHttpClientInstance: OkHttpClient? = null
    private var imageLoaderInstance: ImageLoader? = null

    /**
     * Provides a singleton instance of OkHttpClient configured with a disk cache.
     * @param context The application context, used to access the cache directory.
     * @return A configured OkHttpClient instance.
     */
    fun getOkHttpClient(context: Context): OkHttpClient {
        if (okHttpClientInstance == null) {
            val cacheDirectory = File(context.cacheDir, "http_cache")
            val cache = Cache(cacheDirectory, OKHTTP_CACHE_SIZE_BYTES)
            okHttpClientInstance = OkHttpClient.Builder()
                .cache(cache)
                .build()
        }
        return okHttpClientInstance!!
    }

    /**
     * Provides a singleton instance of Coil's ImageLoader.
     * @param context The application context.
     * @return A configured ImageLoader instance.
     */
    fun getImageLoader(context: Context): ImageLoader {
        if (imageLoaderInstance == null) {
            imageLoaderInstance = ImageLoader.Builder(context)
                // Coil's ImageLoader has its own disk and memory caching.
                // Default disk cache size is 2% of available disk space or 10MB (min) / 250MB (max).
                // Default memory cache size is based on available RAM.
                // No explicit configuration needed here for basic caching as per requirements.
                .build()
        }
        return imageLoaderInstance!!
    }

    /**
     * Clears the OkHttp disk cache.
     * This needs to be called if the existing client instance's cache is to be cleared.
     * Note: This evicts all entries. For more granular control, OkHttp's Cache class offers remove(url).
     */
    fun clearOkHttpCache() {
        okHttpClientInstance?.cache?.evictAll()
    }

    /**
     * Clears Coil's memory and disk caches.
     * @param context The application context, used by Coil's ImageLoader for clearing caches.
     *                Coil's default disk cache instance might require context.
     */
    fun clearCoilCache(context: Context) {
        // Re-get or ensure imageLoaderInstance is initialized, as clearAllCaches might be called
        // before any image is loaded.
        val loader = imageLoaderInstance ?: getImageLoader(context)
        loader.memoryCache?.clear()
        // Coil's disk cache clear operation should be safe.
        // If diskCache is null (e.g., disabled or not yet created), this is a no-op.
        loader.diskCache?.clear()
    }
}
