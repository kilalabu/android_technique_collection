package com.example.android_technique_collection.feature.inbox.ui

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_technique_collection.domain.model.inbox.Email
import com.example.android_technique_collection.feature.inbox.presenter.DetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDetail(state: DetailScreen.State, modifier: Modifier = Modifier) {
    val subject by remember { derivedStateOf { state.email.subject } }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(subject) },
                navigationIcon = {
                    IconButton(onClick = {
                        state.eventSink(DetailScreen.Event.BackClicked)
                    }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding), verticalArrangement = spacedBy(16.dp)) {
            EmailDetailContent(state.email)
        }
    }
}

@Preview
@Composable
private fun EmailDetailPreview() {
    EmailDetail(
        state = DetailScreen.State(
            email = Email(
                id = "1",
                subject = "Meeting re-sched!",
                body =
                    "Hey, I'm going to be out of the office tomorrow. Can we reschedule our meeting for Thursday or next week?",
                sender = "Ali Connors",
                timestamp = "3:00 PM",
                recipients = listOf("all@example.com"),
            ),
            eventSink = {}
        )
    )
}