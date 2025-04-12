package com.example.android_technique_collection.feature.searchphoto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android_technique_collection.feature.searchphoto.component.SearchBar
import com.example.android_technique_collection.feature.searchphoto.section.SearchPhotoResultSection
import com.example.android_technique_collection.feature.searchphoto.state.PagingState
import com.example.android_technique_collection.feature.searchphoto.state.Photo
import com.example.android_technique_collection.feature.searchphoto.state.SearchPhotoViewState
import com.example.android_technique_collection.ui.common.preview.DelayedPreview
import com.example.android_technique_collection.ui.common.theme.Android_technique_collectionTheme

@Composable
fun SearchPhotoScreen(
    viewModel: SearchPhotoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    SearchPhotoScreen(
        uiState = uiState,
        callback = { callback ->
            when (callback) {
                is SearchPhotoScreenCallback.OnSearchTextChanged -> {
                    viewModel.updateQuery(callback.query)
                }

                is SearchPhotoScreenCallback.OnInputDone -> {
                    viewModel.searchPhotos()
                }

                is SearchPhotoScreenCallback.OnReachedToLastItem -> {
                    viewModel.paging()
                }
            }
        },
    )
}

@Composable
private fun SearchPhotoScreen(
    uiState: SearchPhotoViewState,
    callback: (SearchPhotoScreenCallback) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SearchBar(
                searchQuery = uiState.query,
                onSearchTextChanged = {
                    callback(SearchPhotoScreenCallback.OnSearchTextChanged(it))
                },
                onDone = {
                    callback(SearchPhotoScreenCallback.OnInputDone)
                },
                placeHolder = "高解像度写真の検索",
            )
        },
        modifier = modifier,
    ) { padding ->
        when (uiState) {
            is SearchPhotoViewState.Shown -> {
                SearchPhotoResultSection(
                    uiState = uiState,
                    onReachedToLastItem = {
                        callback(SearchPhotoScreenCallback.OnReachedToLastItem)
                    },
                    modifier = Modifier.padding(padding)
                )
            }

            is SearchPhotoViewState.NoResult -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "一致する検索結果がありません",
                    )
                }
            }

            is SearchPhotoViewState.Failure -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = uiState.error,
                    )
                }
            }

            is SearchPhotoViewState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@DelayedPreview(delay = 2000)
@Composable
fun SearchPhotoScreenPreview() {
    Android_technique_collectionTheme {
        SearchPhotoScreen(
            uiState = SearchPhotoViewState.Shown(
                query = "",
                photos = List(5) { index ->
                    Photo(
                        photoId = index.toString(),
                        description = "Image description",
                        likes = 100,
                        imageUrl = "",
                        photographer = "Surface"
                    )
                },
                pagingState = PagingState.READY,
                currentPage = 1,
            ),
            {},
        )
    }
}
