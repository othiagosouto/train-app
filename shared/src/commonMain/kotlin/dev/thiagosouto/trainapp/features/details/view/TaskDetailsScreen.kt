package dev.thiagosouto.trainapp.features.details.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.thiagosouto.trainapp.components.ProgressBar
import dev.thiagosouto.trainapp.components.connectivity.ConnectivityStatusBar
import dev.thiagosouto.trainapp.features.details.TaskDetailsUiState
import dev.thiagosouto.trainapp.features.details.TaskDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun TaskDetailsScreen(
    taskId: String,
    onHomeIconClick: () -> Unit = {},
    viewModel: TaskDetailsViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        TopAppBar(
            navigationIcon = {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "return to home",
                    modifier = Modifier.clickable(enabled = true, onClick = onHomeIconClick)
                )
            },
            title = { Text("Task Details: $taskId") },
            modifier = Modifier.fillMaxWidth()
        )
        ConnectivityStatusBar()
        when (val uiState = state) {
            is TaskDetailsUiState.Loading -> ProgressBar()
            is TaskDetailsUiState.Content -> {
                Card(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                        items(uiState.labels, key = { it.label }) { item ->
                            Column  (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .windowInsetsPadding(WindowInsets.safeDrawing),
                            ) {
                                Text(
                                    item.label,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                Text(item.value)
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(taskId) {
        viewModel.init(taskId)
    }
}
