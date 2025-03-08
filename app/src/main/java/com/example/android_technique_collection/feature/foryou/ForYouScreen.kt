package com.example.android_technique_collection.feature.foryou

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ForYouScreen(
    modifier: Modifier = Modifier,
    viewModel: ForYouViewModel = hiltViewModel(),
) {
    val feedState by viewModel.feedState.collectAsStateWithLifecycle()

    ForYouScreen(
        feedState = feedState,
        modifier = modifier
    )
}

@Composable
fun ForYouScreen(
    feedState: NewsFeedUiState,
    modifier: Modifier = Modifier
) {

    when (feedState) {
        NewsFeedUiState.Loading -> {
            CircularProgressIndicator(modifier = modifier)
        }

        is NewsFeedUiState.Success -> {
            LazyColumn(
                modifier = modifier
            ) {
                items(
                    items = feedState.feed,
                    key = { it.id },
                    contentType = { "newsFeedItem" },
                ) {
                    Column {
                        Text(text = it.title)
                        Text(text = it.content)
                    }
                }
            }
        }
    }
}