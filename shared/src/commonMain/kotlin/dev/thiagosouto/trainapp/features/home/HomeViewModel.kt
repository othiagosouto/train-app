package dev.thiagosouto.trainapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.thiagosouto.trainapp.data.TasksRepository
import dev.thiagosouto.trainapp.domain.DomainException
import dev.thiagosouto.trainapp.features.home.mapper.ErrorMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val tasksRepository: TasksRepository,
    private val errorMapper: ErrorMapper
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeState>(HomeState.Idle)
    val uiState: StateFlow<HomeState> = _uiState

    init {
        viewModelScope.launch {
            tasksRepository.tasks
                .collect { tasks ->
                    val items = tasks.map { task ->
                        HomeState.Content.ItemUiModel(
                            id = task.id,
                            headline = "Train ${task.trainId} - ${task.taskType}",
                            supportingText = "Priority: ${task.priorityLevel}"
                        )
                    }
                    _uiState.emit(
                        HomeState.Content(
                            loading = false,
                            title = "Maintenance Tasks",
                            error = null,
                            tasks = items
                        )
                    )
                }
        }
    }

    fun load() {
        viewModelScope.launch {
            try {
                reduceLoading(_uiState)
                tasksRepository.refresh()
            } catch (exception: DomainException) {
                handleError(_uiState, errorMapper, exception)
            }
        }
    }
}

private fun handleError(
    uiState: MutableStateFlow<HomeState>,
    errorMapper: ErrorMapper,
    exception: DomainException
) {
    val error = errorMapper.apply(exception)
    uiState.value = when (val state = uiState.value) {
        is HomeState.Loading,
        is HomeState.Error,
        is HomeState.Idle -> HomeState.Error(error)

        is HomeState.Content -> state.copy(
            loading = false,
            error = error,
            tasks = emptyList()
        )
    }
}

private suspend fun reduceLoading(uiState: MutableStateFlow<HomeState>) {
    when (val state = uiState.value) {
        is HomeState.Loading,
        is HomeState.Idle,
        is HomeState.Error -> uiState.emit(HomeState.Loading)

        is HomeState.Content -> {
            uiState.emit(
                state.copy(
                    loading = true,
                    error = null,
                    tasks = emptyList()
                )
            )
        }
    }
}
