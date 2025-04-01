package com.example.android_technique_collection.feature.inbox.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
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