package com.example.android_technique_collection.feature.foryou

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ForYouScreen(
    modifier: Modifier = Modifier,
    viewModel: ForYouViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
) {
    val feedState by viewModel.feedState.collectAsStateWithLifecycle()

    ForYouScreen(
        feedState = feedState,
        modifier = modifier,
        onPopBackStack = onPopBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForYouScreen(
    feedState: NewsFeedUiState,
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Feed") },
                navigationIcon = {
                    IconButton(onClick = { onPopBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (feedState) {
            NewsFeedUiState.Loading -> {
                CircularProgressIndicator(modifier = modifier.padding(innerPadding))
            }

            is NewsFeedUiState.Success -> {
                LazyColumn(
                    modifier = modifier.padding(innerPadding)
                ) {
                    items(
                        items = feedState.feed,
                        key = { it.id },
                        contentType = { "newsFeedItem" },
                    ) { userNewsResource ->
                        NewsResourceCard(
                            userNewsResource = userNewsResource,
                            isBookmarked = userNewsResource.isSaved,
                            onClick = {},
                            onToggleBookmark = {},
                            onTopicClick = {},
                            modifier = Modifier
                                .padding(8.dp)
                                .animateItem(),
                        )
                    }
                }
            }
        }
    }
}