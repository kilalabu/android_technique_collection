package com.example.android_technique_collection.feature.materialcompare

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
// M2 Components
import androidx.compose.material.Button as M2Button
import androidx.compose.material.Card as M2Card
import androidx.compose.material.ExtendedFloatingActionButton as M2ExtendedFAB
import androidx.compose.material.Icon as M2Icon
import androidx.compose.material.IconButton as M2IconButton
import androidx.compose.material.MaterialTheme as M2Theme
import androidx.compose.material.OutlinedButton as M2OutlinedButton
import androidx.compose.material.OutlinedTextField as M2OutlinedTextField
import androidx.compose.material.Scaffold as M2Scaffold
import androidx.compose.material.Text as M2Text
import androidx.compose.material.TextButton as M2TextButton
import androidx.compose.material.TopAppBar as M2TopAppBar
// M3 Components
import androidx.compose.material3.Button as M3Button
import androidx.compose.material3.Card as M3Card
import androidx.compose.material3.ExtendedFloatingActionButton as M3ExtendedFAB
import androidx.compose.material3.Icon as M3Icon
import androidx.compose.material3.IconButton as M3IconButton
import androidx.compose.material3.MaterialTheme as M3Theme
import androidx.compose.material3.OutlinedButton as M3OutlinedButton
import androidx.compose.material3.OutlinedTextField as M3OutlinedTextField
import androidx.compose.material3.Scaffold as M3Scaffold
import androidx.compose.material3.Switch as M3Switch
import androidx.compose.material3.Text as M3Text
import androidx.compose.material3.TextButton as M3TextButton
import androidx.compose.material3.TopAppBar as M3TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
            M3Text(text = if (isMaterial3) "Material 3" else "Material 2")
            Spacer(modifier = Modifier.width(8.dp))
            M3Switch(
                checked = isMaterial3,
                onCheckedChange = { isMaterial3 = it }
            )
        }

        if (isMaterial3) {
            M3Theme {
                CompareScaffold(isMaterial3 = true)
            }
        } else {
            M2Theme {
                CompareScaffold(isMaterial3 = false)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareScaffold(isMaterial3: Boolean) {
    if (isMaterial3) {
        M3Scaffold(
            topBar = {
                M3TopAppBar(
                    title = { M3Text("Material 3 Components") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = M3Theme.colorScheme.primaryContainer,
                        titleContentColor = M3Theme.colorScheme.primary,
                    ),
                    navigationIcon = {
                        M3IconButton(onClick = { }) {
                            M3Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButton = {
                M3ExtendedFAB(
                    onClick = { },
                    icon = { M3Icon(Icons.Filled.Favorite, contentDescription = "Favorite") },
                    text = { M3Text("FAB") }
                )
            }
        ) { paddingValues ->
            ComponentList(
                isMaterial3 = true,
                modifier = Modifier.padding(paddingValues)
            )
        }
    } else {
        M2Scaffold(
            topBar = {
                M2TopAppBar(
                    title = { M2Text("Material 2 Components") },
                    navigationIcon = {
                        M2IconButton(onClick = { }) {
                            M2Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButton = {
                M2ExtendedFAB(
                    text = { M2Text("FAB") },
                    onClick = { },
                    icon = { M2Icon(Icons.Filled.Favorite, contentDescription = "Favorite") }
                )
            }
        ) { paddingValues ->
            // ★ M2/M3で共通のコンポーネントリストを呼び出す
            ComponentList(
                isMaterial3 = false,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun ComponentList(isMaterial3: Boolean, modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp) // FABとコンテンツが被らないように
    ) {
        item {
            M3Text(
                "Buttons",
                style = if (isMaterial3) M3Theme.typography.titleLarge else M2Theme.typography.h6
            )
        }
        item {
            // ★ isMaterial3に応じて、表示するボタンを切り替え
            if (isMaterial3) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    M3Button(onClick = { }) { M3Text("Filled") }
                    M3OutlinedButton(onClick = { }) { M3Text("Outlined") }
                    M3TextButton(onClick = { }) { M3Text("Text") }
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    M2Button(onClick = { }) { M2Text("Filled (Contained)") }
                    M2OutlinedButton(onClick = { }) { M2Text("Outlined") }
                    M2TextButton(onClick = { }) { M2Text("Text") }
                }
            }
        }

        item {
            M3Text(
                "Text Field",
                style = if (isMaterial3) M3Theme.typography.titleLarge else M2Theme.typography.h6
            )
        }
        item {
            if (isMaterial3) {
                M3OutlinedTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    label = { M3Text("OutlinedTextField") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                M2OutlinedTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    label = { M2Text("OutlinedTextField") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            M3Text(
                "Cards",
                style = if (isMaterial3) M3Theme.typography.titleLarge else M2Theme.typography.h6
            )
        }
        item {
            if (isMaterial3) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    M3Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            M3Text("Card")
                        }
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    M2Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            M2Text("Card")
                        }
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