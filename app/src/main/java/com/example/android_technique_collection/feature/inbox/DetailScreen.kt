package com.example.android_technique_collection.feature.inbox

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
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.Navigator
import com.example.android_technique_collection.data.repository.EmailRepositoryImpl
import com.example.android_technique_collection.domain.model.inbox.Email
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailScreen(val emailId: String) : Screen {
    data class State(val email: Email) : CircuitUiState
}

class DetailPresenter(
    private val screen: DetailScreen,
    private val emailRepository: EmailRepositoryImpl
) : Presenter<DetailScreen.State> {
    @Composable
    override fun present(): DetailScreen.State {
        val email = emailRepository.getEmail(screen.emailId)
        return DetailScreen.State(email)
    }

    class Factory(private val emailRepository: EmailRepositoryImpl) : Presenter.Factory {
        override fun create(screen: Screen, navigator: Navigator, context: CircuitContext): Presenter<*>? {
            return when (screen) {
                is DetailScreen -> return DetailPresenter(screen, emailRepository)
                else -> null
            }
        }
    }
}

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
                        //state.eventSink(DetailScreen.Event.BackClicked)
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
