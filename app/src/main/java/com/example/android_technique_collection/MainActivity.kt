package com.example.android_technique_collection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.android_technique_collection.data.repository.EmailRepositoryImpl
import com.example.android_technique_collection.feature.inbox.ui.EmailDetail
import com.example.android_technique_collection.feature.inbox.ui.Inbox
import com.example.android_technique_collection.feature.inbox.presenter.DetailPresenter
import com.example.android_technique_collection.feature.inbox.presenter.DetailScreen
import com.example.android_technique_collection.feature.inbox.presenter.InboxPresenter
import com.example.android_technique_collection.feature.inbox.presenter.InboxScreen
import com.example.android_technique_collection.ui.common.theme.Android_technique_collectionTheme
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val emailRepository = EmailRepositoryImpl()
        val circuit: Circuit =
            Circuit.Builder()
                .addPresenterFactory(InboxPresenter.Factory(emailRepository))
                .addPresenterFactory(DetailPresenter.Factory(emailRepository))
                .addUi<InboxScreen, InboxScreen.State> { state, modifier -> Inbox(state, modifier) }
                .addUi<DetailScreen, DetailScreen.State> { state, modifier -> EmailDetail(state, modifier) }
                .build()
        setContent {
            Android_technique_collectionTheme {
                val backStack = rememberSaveableBackStack(root = InboxScreen)
                val navigator = rememberCircuitNavigator(backStack)
                CircuitCompositionLocals(circuit) {
                    NavigableCircuitContent(navigator, backStack)
                }
            }
        }
    }
}
