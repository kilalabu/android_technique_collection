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
import com.example.android_technique_collection.feature.chart.ChartScreen
import com.example.android_technique_collection.feature.home.HomeScreen
import com.example.android_technique_collection.feature.materialcompare.MaterialCompareScreen
import com.example.android_technique_collection.feature.searchphoto.SearchPhotoScreen
import com.example.android_technique_collection.ui.common.theme.Android_technique_collectionTheme
import com.example.android_technique_collection.ui.common.route.ScreenRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_technique_collectionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.HomeScreen.route
                    ) {
                        composable(ScreenRoute.HomeScreen.route) {
                            HomeScreen(
                                onNavigateToSearch = {
                                    navController.navigate(ScreenRoute.SearchScreen.route)
                                },
                                onNavigateToChart = {
                                    navController.navigate(ScreenRoute.ChartScreen.route)
                                },
                                onNavigateToMaterialCompare = {
                                    navController.navigate(ScreenRoute.MaterialCompareScreen.route)
                                }
                            )
                        }
                        composable(ScreenRoute.SearchScreen.route) {
                            SearchPhotoScreen()
                        }
                        composable(ScreenRoute.ChartScreen.route) {
                            ChartScreen()
                        }
                        composable(ScreenRoute.MaterialCompareScreen.route) {
                            MaterialCompareScreen()
                        }
                    }
                }
            }
        }
    }
}
