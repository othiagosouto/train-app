package dev.thiagosouto.trainapp.components.connectivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jordond.connectivity.Connectivity
import dev.thiagosouto.trainapp.data.TasksRepository
import dev.thiagosouto.trainapp.domain.DomainException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ConnectivityViewModel(
    connectivity: Connectivity,
    private val repository: TasksRepository
) : ViewModel() {
    private val _hasError = MutableStateFlow(false)
    val hasError: StateFlow<Boolean> = _hasError

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    init {
        connectivity.start()
        viewModelScope.launch {
            initConnectivity(connectivity)
            initRepositoryCollection()
        }
    }

    private suspend fun initRepositoryCollection(): Nothing {
        repository.tasks.collect {
            clearError()
        }
    }

    private suspend fun initConnectivity(connectivity: Connectivity) {
        connectivity.statusUpdates.distinctUntilChanged().collect { status ->
            when (status) {
                is Connectivity.Status.Connected -> {
                    if (hasError.value) {
                        load()
                    }
                }

                is Connectivity.Status.Disconnected -> {
                    emitError(NO_INTERNET_MESSAGE)
                }
            }
        }
    }

    @Suppress("SwallowedException") // This catch is not expected to throw error so its ok
    private fun load(retry: Int = 0) {
        viewModelScope.launch {
            if (retry >= MAX_RETRY) {
                emitError(FAILED_TO_FETCH_MESSAGE_RETRY)
                return@launch
            }
            try {
                repository.refresh()
                clearError()
            } catch (exception: DomainException) {
                emitError(FAILED_TO_FETCH_MESSAGE)
                load(retry + 1)
            }
        }
    }

    private fun clearError() = viewModelScope.launch {
        _error.emit("")
        _hasError.emit(false)
    }

    private fun emitError(message: String) {
        viewModelScope.launch {
            _error.emit(message)
            _hasError.emit(true)
        }
    }

    private companion object {
        const val NO_INTERNET_MESSAGE = "No internet connection, offline mode"
        const val FAILED_TO_FETCH_MESSAGE = "Failed to automatically fetch data, trying again"
        const val FAILED_TO_FETCH_MESSAGE_RETRY =
            "Failed to automatically fetch data many times, please check your connection"
        const val MAX_RETRY = 3
    }
}
