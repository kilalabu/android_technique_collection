package com.example.android_technique_collection.ui.common.route

sealed class ScreenRoute(val route: String) {
    data object HomeScreen: ScreenRoute("home_screen")
    data object SearchScreen: ScreenRoute("search_screen")
    data object ChartScreen: ScreenRoute("chart_screen")
    data object MaterialCompareScreen: ScreenRoute("material_compare_screen")
}
