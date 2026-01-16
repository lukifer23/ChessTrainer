package com.chesstrainer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Settings functionality coming soon...")

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onNavigateBack) {
            Text("Back")
        }
    }
}