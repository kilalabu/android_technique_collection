package com.example.cachingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cachingapp.ui.theme.CachingAppTheme

/**
 * MainActivity: Defines the Jetpack Compose UI and connects to the MainViewModel.
 * This screen demonstrates caching strategies for JSON data and images.
 */
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CachingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CachingAppScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun CachingAppScreen(viewModel: MainViewModel) {
    val jsonResult by viewModel.jsonResult.collectAsState()
    val imageAUrl by viewModel.imageAUrl.collectAsState()
    val imageBUrl by viewModel.imageBUrl.collectAsState()
    val logs by viewModel.logMessages.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { viewModel.fetchUserJson() }) { Text("Fetch User (JSON)") }
            Button(onClick = { viewModel.fetchPostJson() }) { Text("Fetch Post (JSON)") }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { viewModel.loadImageA() }) { Text("Load Image A") }
            Button(onClick = { viewModel.loadImageB() }) { Text("Load Image B") }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.clearAllCaches() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Clear All Caches")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // JSON Text Area
        Text("JSON Data:", fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Takes up available space
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        ) {
            Text(
                text = jsonResult,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Image View Area
        Text("Images:", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Image A
            Box(modifier = Modifier.weight(1f).padding(4.dp)) {
                if (imageAUrl != null) {
                    // Coil's AsyncImage handles image loading and caching.
                    // Coil automatically performs memory and disk caching.
                    // - First load: Fetches from network, stores in memory & disk cache.
                    // - Subsequent loads: Checks memory cache, then disk cache, then network.
                    // Logs in Logcat (tag "Coil") will show cache hits/misses (e.g., "MEMRY CACHE HIT", "DISK CACHE HIT").
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageAUrl)
                            .crossfade(true)
                            // Add listener to log Coil events if needed for UI logs (not required by spec)
                            .build(),
                        contentDescription = "Image A",
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .border(1.dp, Color.LightGray),
                        contentScale = ContentScale.Crop,
                        imageLoader = HttpClientProvider.getImageLoader(context) // Use shared ImageLoader
                    )
                } else {
                    Box(modifier = Modifier.height(150.dp).fillMaxWidth().border(1.dp, Color.LightGray), contentAlignment = Alignment.Center) {
                        Text("Image A will load here")
                    }
                }
            }

            // Image B
            Box(modifier = Modifier.weight(1f).padding(4.dp)) {
                if (imageBUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageBUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Image B",
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .border(1.dp, Color.LightGray),
                        contentScale = ContentScale.Crop,
                        imageLoader = HttpClientProvider.getImageLoader(context) // Use shared ImageLoader
                    )
                } else {
                    Box(modifier = Modifier.height(150.dp).fillMaxWidth().border(1.dp, Color.LightGray), contentAlignment = Alignment.Center) {
                        Text("Image B will load here")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Status Log Area
        Text("Status Logs:", fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f) // Takes up more space
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize(), reverseLayout = false) { // Show newest logs at top by not reversing
                items(logs) { logEntry ->
                    Text(logEntry, fontSize = 12.sp)
                    Divider()
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun DefaultPreview() {
    // Create a dummy ViewModel for preview if needed, or use a simpler preview.
    // For now, let's just show the theme.
    // For a more complete preview, you'd mock the ViewModel and its StateFlows.
    CachingAppTheme {
         Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier=Modifier.padding(16.dp)){
                Text("Preview: UI elements will be shown here.", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(20.dp))
                Text("JSON Area Placeholder", modifier = Modifier.fillMaxWidth().height(100.dp).border(1.dp, Color.Gray))
                Spacer(modifier = Modifier.height(20.dp))
                Text("Image Area Placeholder", modifier = Modifier.fillMaxWidth().height(100.dp).border(1.dp, Color.Gray))
                Spacer(modifier = Modifier.height(20.dp))
                Text("Log Area Placeholder", modifier = Modifier.fillMaxWidth().height(150.dp).border(1.dp, Color.Gray))
            }
         }
    }
}
