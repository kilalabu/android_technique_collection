package com.example.android_technique_collection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_technique_collection.data.repository.EmailRepositoryImpl
import com.example.android_technique_collection.feature.chart.ChartScreen
import com.example.android_technique_collection.feature.home.HomeScreen
import com.example.android_technique_collection.feature.inbox.DetailPresenter
import com.example.android_technique_collection.feature.inbox.DetailScreen
import com.example.android_technique_collection.feature.inbox.EmailDetail
import com.example.android_technique_collection.feature.inbox.Inbox
import com.example.android_technique_collection.feature.inbox.InboxPresenter
import com.example.android_technique_collection.feature.inbox.InboxScreen
import com.example.android_technique_collection.feature.searchphoto.SearchPhotoScreen
import com.example.android_technique_collection.ui.common.theme.Android_technique_collectionTheme
import com.example.android_technique_collection.ui.common.route.ScreenRoute
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
