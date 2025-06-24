package com.example.cachingapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ViewModel for MainActivity. Manages UI state and interacts with DataRepository.
 *
 * @param application The application instance, used to get context for DataRepository.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataRepository(application.applicationContext)

    // UI State: JSON result display
    private val _jsonResult = MutableStateFlow("JSON data will appear here.")
    val jsonResult: StateFlow<String> = _jsonResult.asStateFlow()

    // UI State: Image A URL for Coil
    // Using a timestamp query parameter to bypass Coil's cache for demonstration when needed,
    // but for normal load, we'll use the base URL. Coil handles its own caching.
    private val _imageAUrl = MutableStateFlow<String?>(null)
    val imageAUrl: StateFlow<String?> = _imageAUrl.asStateFlow()

    // UI State: Image B URL for Coil
    private val _imageBUrl = MutableStateFlow<String?>(null)
    val imageBUrl: StateFlow<String?> = _imageBUrl.asStateFlow()

    // UI State: Log messages
    private val _logMessages = MutableStateFlow<List<String>>(emptyList())
    val logMessages: StateFlow<List<String>> = _logMessages.asStateFlow()

    // Data sources
    companion object {
        const val USER_JSON_URL = "https://jsonplaceholder.typicode.com/users/1"
        const val POST_JSON_URL = "https://jsonplaceholder.typicode.com/posts/1"
        const val IMAGE_A_URL = "https://picsum.photos/300"
        const val IMAGE_B_URL = "https://picsum.photos/300?grayscale"
    }

    init {
        addLog("ViewModel initialized. Ready to fetch data.")
    }

    /**
     * Adds a log message to the beginning of the log list, including a timestamp.
     * @param message The message to log.
     */
    private fun addLog(message: String) {
        val timestamp = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())
        _logMessages.update { currentLogs -> listOf("[$timestamp] $message") + currentLogs }
    }

    /**
     * Fetches User JSON data.
     */
    fun fetchUserJson() {
        addLog("Attempting to fetch User JSON...")
        viewModelScope.launch {
            when (val result = repository.fetchJsonData(USER_JSON_URL)) {
                is DataRepository.FetchResult.Success -> {
                    _jsonResult.value = result.data
                    addLog(result.sourceMessage) // Log cache hit/miss message from repository
                    addLog("User JSON loaded successfully.")
                }
                is DataRepository.FetchResult.Error -> {
                    _jsonResult.value = result.errorMessage
                    addLog(result.errorMessage)
                }
            }
        }
    }

    /**
     * Fetches Post JSON data.
     */
    fun fetchPostJson() {
        addLog("Attempting to fetch Post JSON...")
        viewModelScope.launch {
            when (val result = repository.fetchJsonData(POST_JSON_URL)) {
                is DataRepository.FetchResult.Success -> {
                    _jsonResult.value = result.data
                    addLog(result.sourceMessage) // Log cache hit/miss message from repository
                    addLog("Post JSON loaded successfully.")
                }
                is DataRepository.FetchResult.Error -> {
                    _jsonResult.value = result.errorMessage
                    addLog(result.errorMessage)
                }
            }
        }
    }

    /**
     * Triggers loading of Image A.
     * Coil handles caching; we just provide the URL.
     * Adding a unique timestamp as a query parameter can be used to force a network request,
     * bypassing caches if needed for specific testing, but not for standard behavior.
     */
    fun loadImageA() {
        addLog("Requesting Image A. Coil will handle fetching and caching.")
        // Forcing a fresh load by adding a timestamp query param - useful for demo.
        // For normal operation, just use IMAGE_A_URL.
        // _imageAUrl.value = IMAGE_A_URL + "?time=" + System.currentTimeMillis()
        _imageAUrl.value = IMAGE_A_URL // Let Coil do its job for standard caching behavior
        // Note: Coil logs can be observed in Logcat (tag "Coil") to see cache hits/misses.
        // We can add a log here, but the actual cache hit/miss for Coil is internal to it.
        addLog("Coil is loading Image A from: $IMAGE_A_URL")
    }

    /**
     * Triggers loading of Image B.
     */
    fun loadImageB() {
        addLog("Requesting Image B. Coil will handle fetching and caching.")
        _imageBUrl.value = IMAGE_B_URL // Let Coil do its job
        addLog("Coil is loading Image B from: $IMAGE_B_URL")
    }

    /**
     * Clears all caches (Memory, OkHttp Disk, Coil Memory/Disk).
     */
    fun clearAllCaches() {
        addLog("Attempting to clear all caches...")
        viewModelScope.launch {
            val resultMessage = repository.clearAllCaches()
            addLog(resultMessage)
            _jsonResult.value = "Caches cleared. Fetch new data."
            // Optionally clear image URLs to remove them from UI until next load
            _imageAUrl.value = null
            _imageBUrl.value = null
        }
    }
}
