package dev.thiagosouto.trainapp.fakes

import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

class ConnectivityFake(
    initialValue: Connectivity.Status
) : Connectivity {
    private val _monitoring: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _statusUpdates: MutableStateFlow<Connectivity.Status>
    override val monitoring: StateFlow<Boolean>
    override val statusUpdates: SharedFlow<Connectivity.Status>

    init {
        _statusUpdates = MutableStateFlow(initialValue)
        monitoring = _monitoring
        statusUpdates = _statusUpdates
    }

    override fun start() {
        _monitoring.value = true
    }


    override suspend fun status(): Connectivity.Status {
        return _statusUpdates.value
    }

    override fun stop() {
        _monitoring.value = false
    }

    suspend fun emitStatus(status: Connectivity.Status) {
        _statusUpdates.emit(status)
    }
}
