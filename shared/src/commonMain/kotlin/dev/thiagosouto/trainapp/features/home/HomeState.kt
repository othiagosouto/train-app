package dev.thiagosouto.trainapp.features.home

import dev.thiagosouto.trainapp.components.error.ErrorUiModel

internal sealed class HomeState {
    data object Idle : HomeState()
    data object Loading : HomeState()
    data class Error(val error: ErrorUiModel) : HomeState()
    data class Content(
        val loading: Boolean,
        val title: String,
        val error: ErrorUiModel?,
        val tasks: List<ItemUiModel>
    ) : HomeState() {
        data class ItemUiModel(
            val id: String,
            val headline: String,
            val supportingText: String,
        )
    }
}
