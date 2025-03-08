package com.example.android_technique_collection.feature.foryou

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp

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
                    ) {
                        Column {
                            Text(text = it.toString())
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 16.dp),
                            )
                        }
                    }
                }
            }
        }
    }

}