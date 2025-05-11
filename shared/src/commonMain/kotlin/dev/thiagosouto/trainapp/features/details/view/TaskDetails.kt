package dev.thiagosouto.trainapp.features.details.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.thiagosouto.trainapp.components.connectivity.ConnectivityStatusBar

@Composable
internal fun TaskDetailsScreen(
    taskId: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        TopAppBar(
            title = { Text("Task Details: $taskId") },
            modifier = Modifier.fillMaxWidth()
        )
        ConnectivityStatusBar()
    }
}
