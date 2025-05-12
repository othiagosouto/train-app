package dev.thiagosouto.trainapp.data.local

import dev.thiagosouto.trainapp.domain.Task
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Caches Tasks in memory
 *
 * This class is used to cache tasks in memory and emit them as a flow.
 */
class TasksCache {
    private val _tasks = MutableSharedFlow<List<Task>>(replay = 1)
    val tasks = _tasks.asSharedFlow()

    suspend fun emit(items: List<Task>) {
        _tasks.emit(items)
    }
}
