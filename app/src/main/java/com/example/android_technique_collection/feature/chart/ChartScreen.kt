package com.example.android_technique_collection.feature.chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android_technique_collection.feature.chart.component.BarChart
import com.example.android_technique_collection.feature.chart.component.PieCharts
import com.example.android_technique_collection.feature.chart.state.ChartState
import com.example.android_technique_collection.feature.chart.state.PieChartsElement
import com.example.android_technique_collection.ui.common.theme.Android_technique_collectionTheme

@Composable
fun ChartScreen(
    viewModel: ChartViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    ChartScreen(
        uiState = uiState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChartScreen(
    uiState: ChartState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "グラフ")
                },
            )
        },
        modifier = modifier,
    ) { padding ->
        Column(
            modifier = modifier.padding(padding)
        ) {
            when(uiState) {
                is ChartState.Shown -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                       PieCharts(
                           elements = uiState.pieChartsElements,
                           modifier = Modifier.size(100.dp)
                       )
                        Spacer(modifier = Modifier.height(16.dp))
                        BarChart()
                    }
                }
                is ChartState.Failure -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = uiState.error,
                        )
                    }
                }
                ChartState.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChartScreenPreview() {
    Android_technique_collectionTheme {
        ChartScreen(
            uiState = ChartState.Shown(
                pieChartsElements = PieChartsElement.fake()
            )
        )
    }
}
