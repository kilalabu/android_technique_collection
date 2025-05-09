package com.example.android_technique_collection.feature.inbox.presenter

import androidx.compose.runtime.Composable
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
data class DetailScreen(val emailId: String) : Screen {
    data class State(
        val email: Email,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data object BackClicked : Event()
    }
}

class DetailPresenter(
    private val screen: DetailScreen,
    private val navigator: Navigator,
    private val emailRepository: EmailRepositoryImpl
) : Presenter<DetailScreen.State> {
    @Composable
    override fun present(): DetailScreen.State {
        val email = emailRepository.getEmail(screen.emailId)
        return DetailScreen.State(email) { event ->
            when (event) {
                DetailScreen.Event.BackClicked -> navigator.pop()
            }
        }
    }

    class Factory(private val emailRepository: EmailRepositoryImpl) : Presenter.Factory {
        override fun create(screen: Screen, navigator: Navigator, context: CircuitContext): Presenter<*>? {
            return when (screen) {
                is DetailScreen -> return DetailPresenter(screen, navigator, emailRepository)
                else -> null
            }
        }
    }
}