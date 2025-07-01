package com.example.android_technique_collection.feature.materialcompare

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme as Material3Theme
import androidx.compose.material3.Text as Material3Text
import androidx.compose.material3.TopAppBar as Material3TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.Button as Material2Button
import androidx.compose.material.Card as Material2Card
import androidx.compose.material.ExtendedFloatingActionButton as Material2ExtendedFloatingActionButton
import androidx.compose.material.Icon as Material2Icon
import androidx.compose.material.IconButton as Material2IconButton
import androidx.compose.material.MaterialTheme as Material2Theme
import androidx.compose.material.Scaffold as Material2Scaffold
import androidx.compose.material.Text as Material2Text
import androidx.compose.material.TextField as Material2TextField
import androidx.compose.material.TopAppBar as Material2TopAppBar
import androidx.compose.material3.Button as Material3Button
import androidx.compose.material3.Card as Material3Card
import androidx.compose.material3.ElevatedCard as Material3ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton as Material3ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton as Material3FilledTonalButton
import androidx.compose.material3.OutlinedButton as Material3OutlinedButton
import androidx.compose.material3.OutlinedTextField as Material3OutlinedTextField
import androidx.compose.material3.Switch as Material3Switch
import androidx.compose.material3.TextButton as Material3TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialCompareScreen() {
    var isMaterial3 by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Material3Text(text = if (isMaterial3) "Material 3" else "Material 2")
            Spacer(modifier = Modifier.width(8.dp))
            Material3Switch(
                checked = isMaterial3,
                onCheckedChange = { isMaterial3 = it }
            )
        }

        if (isMaterial3) {
            Material3Components()
        } else {
            // Material2 uses its own Scaffold, so we need to provide a separate one
            Material2Theme {
                Material2Scaffold(
                    topBar = {
                        Material2TopAppBar(
                            title = { Material2Text("Material 2 Components") }
                        )
                    },
                    floatingActionButton = {
                        Material2ExtendedFloatingActionButton(
                            text = { Material2Text("FAB") },
                            onClick = { /* No action needed for this sample */ },
                            icon = { Material2Icon(Icons.Filled.Favorite, contentDescription = "Favorite") }
                        )
                    }
                ) { paddingValues ->
                    Material2Components(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}

@Composable
fun Material2Components(modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Material2Button(onClick = { /* No action needed for this sample */ }) {
            Material2Text("Button")
        }
        Material2TextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Material2Text("TextField") }
        )
        Material2Card(elevation = 4.dp) {
            Column(modifier = Modifier.padding(16.dp)) {
                Material2Text("Card Title")
                Material2Text("This is some card content in Material 2.")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3Components(modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("") }
    Material3Theme {
        androidx.compose.material3.Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                Material3TopAppBar(
                    title = { Material3Text("Material 3 Components") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Material3Theme.colorScheme.primaryContainer,
                        titleContentColor = Material3Theme.colorScheme.primary,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { /* No action needed for this sample */ }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButton = {
                Material3ExtendedFloatingActionButton(
                    onClick = { /* No action needed for this sample */ },
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorite") },
                    text = { Material3Text("FAB") }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Material3Button(onClick = { /* No action needed for this sample */ }) {
                    Material3Text("Button (Filled)")
                }
                Material3FilledTonalButton(onClick = { /* No action needed for this sample */ }) {
                    Material3Text("Button (Filled Tonal)")
                }
                Material3OutlinedButton(onClick = { /* No action needed for this sample */ }) {
                    Material3Text("Button (Outlined)")
                }
                Material3TextButton(onClick = { /* No action needed for this sample */ }) {
                    Material3Text("Button (Text)")
                }
                Material3OutlinedTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    label = { Material3Text("OutlinedTextField") }
                )
                Material3Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Material3Text("Card Title (Elevated)")
                        Material3Text("This is some card content in Material 3.")
                    }
                }
                Material3ElevatedCard {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Material3Text("Card Title (Elevated)")
                        Material3Text("This is some card content in Material 3 (Elevated Card).")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MaterialCompareScreenPreview() {
    MaterialCompareScreen()
}

@Preview(showBackground = true, name = "Material 2 Components Preview")
@Composable
fun Material2ComponentsPreview() {
    Material2Theme {
        Material2Components()
    }
}

@Preview(showBackground = true, name = "Material 3 Components Preview")
@Composable
fun Material3ComponentsPreview() {
    Material3Components()
}
