package com.example.android_technique_collection.feature.inbox.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_technique_collection.domain.model.inbox.Email
import com.example.android_technique_collection.feature.inbox.presenter.InboxScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inbox(state: InboxScreen.State, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("Inbox") }) }) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(state.emails) { email ->
                EmailItem(
                    email = email,
                    onClick = { state.eventSink(InboxScreen.Event.EmailClicked(email.id)) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun InboxPreview() {
    Inbox(
        state = InboxScreen.State(
            emails = listOf(
                Email(
                    id = "1",
                    subject = "Meeting re-sched!",
                    body =
                        "Hey, I'm going to be out of the office tomorrow. Can we reschedule our meeting for Thursday or next week?",
                    sender = "Ali Connors",
                    timestamp = "3:00 PM",
                    recipients = listOf("all@example.com"),
                ),
                Email(
                    id = "2",
                    subject = "Meeting re-sched!",
                    body =
                        "Hey, I'm going to be out of the office tomorrow. Can we reschedule our meeting for Thursday or next week?",
                    sender = "Ali Connors",
                    timestamp = "3:00 PM",
                    recipients = listOf("all@example.com"),
                ),
                Email(
                    id = "3",
                    subject = "Meeting re-sched!",
                    body =
                        "Hey, I'm going to be out of the office tomorrow. Can we reschedule our meeting for Thursday or next week?",
                    sender = "Ali Connors",
                    timestamp = "3:00 PM",
                    recipients = listOf("all@example.com"),
                ),
            ),
            eventSink = {}
        )
    )

}