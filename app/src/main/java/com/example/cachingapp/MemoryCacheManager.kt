package com.example.cachingapp

import androidx.collection.LruCache

/**
 * Manages an in-memory cache for string data (e.g., JSON responses).
 * This class is a singleton to ensure a single cache instance throughout the application.
 */
object MemoryCacheManager {

    private const val MAX_CACHE_SIZE = 4 * 1024 * 1024 // 4MB cache size for strings
    private val cache: LruCache<String, String> = LruCache(MAX_CACHE_SIZE)

    /**
     * Retrieves an item from the memory cache.
     * @param key The key for the cached item.
     * @return The cached string data, or null if not found.
     */
    fun get(key: String): String? {
        return cache.get(key)
    }

    /**
     * Adds or updates an item in the memory cache.
     * @param key The key for the item to cache.
     * @param value The string data to cache.
     */
    fun put(key: String, value: String) {
        cache.put(key, value)
    }

    /**
     * Removes all items from the memory cache.
     */
    fun evictAll() {
        cache.evictAll()
    }
}
