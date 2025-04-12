package com.example.android_technique_collection.feature.searchphoto

sealed interface SearchPhotoScreenCallback {
    data class OnSearchTextChanged(
        val query: String,
    ) : SearchPhotoScreenCallback

    data object OnInputDone: SearchPhotoScreenCallback

    data object OnReachedToLastItem: SearchPhotoScreenCallback
}