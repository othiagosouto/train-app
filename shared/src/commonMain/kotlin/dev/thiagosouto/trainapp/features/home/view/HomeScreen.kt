package dev.thiagosouto.trainapp.features.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dev.thiagosouto.trainapp.components.connectivity.ConnectivityStatusBar
import dev.thiagosouto.trainapp.components.error.ErrorScreen
import dev.thiagosouto.trainapp.components.ProgressBar
import dev.thiagosouto.trainapp.components.TasksList
import dev.thiagosouto.trainapp.features.home.HomeState
import dev.thiagosouto.trainapp.features.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onItemClick: (String) -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        TopAppBar(
            title = { Text(state.value.title) },
            modifier = Modifier.fillMaxWidth()
        )
        ConnectivityStatusBar()
        when (val uiState = state.value) {
            is HomeState.Idle -> LaunchedEffect(true) { viewModel.load() }
            is HomeState.Loading -> ProgressBar()
            is HomeState.Error -> ErrorScreen(uiState.error)
            is HomeState.Content -> TasksList(uiState.tasks, onItemClick)
        }
    }
}
