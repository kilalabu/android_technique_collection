package com.example.android_technique_collection.feature.inbox

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_technique_collection.data.repository.EmailRepositoryImpl
import com.example.android_technique_collection.domain.model.inbox.Email
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object InboxScreen : Screen {
    data class State(
        val emails: List<Email>,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class EmailClicked(val emailId: String) : Event()
    }
}

class InboxPresenter(
    private val navigator: Navigator,
    private val emailRepository: EmailRepositoryImpl
) : Presenter<InboxScreen.State> {
    @Composable
    override fun present(): InboxScreen.State {
        val emails by produceState<List<Email>>(initialValue = emptyList()) {
            value = emailRepository.getEmails()
        }
        return InboxScreen.State(emails) { event ->
            when (event) {
                is InboxScreen.Event.EmailClicked -> navigator.goTo(DetailScreen(event.emailId))
            }
        }
    }

    class Factory(private val emailRepository: EmailRepositoryImpl) : Presenter.Factory {
        override fun create(
            screen: Screen,
            navigator: Navigator,
            context: CircuitContext,
        ): Presenter<*>? {
            return when (screen) {
                InboxScreen -> return InboxPresenter(navigator, emailRepository)
                else -> null
            }
        }
    }
}

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