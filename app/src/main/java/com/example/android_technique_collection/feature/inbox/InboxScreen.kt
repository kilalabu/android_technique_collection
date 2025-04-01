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
import com.example.android_technique_collection.data.repository.EmailRepositoryImpl
import com.example.android_technique_collection.domain.model.inbox.Email
import com.example.android_technique_collection.domain.repository.EmailRepository
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object InboxScreen : Screen {
    data class State(
        val emails: List<Email>
    ) : CircuitUiState
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
        return InboxScreen.State(emails)
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
                EmailItem(email)
            }
        }
    }
}