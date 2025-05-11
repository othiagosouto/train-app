package dev.thiagosouto.trainapp.features.home

import androidx.compose.runtime.Immutable
import dev.thiagosouto.trainapp.components.error.ErrorUiModel

internal sealed class HomeState(val title: String = "Maintenance Tasks") {
    data object Idle : HomeState()
    data object Loading : HomeState()
    data class Error(val error: ErrorUiModel) : HomeState()

    data class Content(
        val loading: Boolean,
        val error: ErrorUiModel?,
        val tasks: List<TaskItemUiModel>
    ) : HomeState()
}

@Immutable
data class TaskItemUiModel(
    val id: String,
    val headline: String,
    val supportingText: String,
)
