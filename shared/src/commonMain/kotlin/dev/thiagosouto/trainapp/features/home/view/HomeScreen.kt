package dev.thiagosouto.trainapp.features.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dev.thiagosouto.trainapp.components.error.ErrorScreen
import dev.thiagosouto.trainapp.components.ProgressBar
import dev.thiagosouto.trainapp.components.TasksList
import dev.thiagosouto.trainapp.features.home.HomeState
import dev.thiagosouto.trainapp.features.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state = viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        when (val uiState = state.value) {
            is HomeState.Idle -> LaunchedEffect(true) { viewModel.load() }
            is HomeState.Loading -> ProgressBar()
            is HomeState.Error -> ErrorScreen(uiState.error)
            is HomeState.Content -> TasksList(uiState.tasks)
        }
    }

}
