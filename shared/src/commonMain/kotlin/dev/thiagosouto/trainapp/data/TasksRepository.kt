package dev.thiagosouto.trainapp.data

import dev.thiagosouto.trainapp.domain.Task
import kotlinx.coroutines.flow.SharedFlow

/**
 * Repository interface for tasks
 *
 * Provides abstraction for interacting with tasks
 */
interface TasksRepository {
    /**
     * Flow of tasks
     *
     * This flow emits a list of tasks available in the repository.
     */
    val tasks: SharedFlow<List<Task>>

    /**
     * Refreshes the tasks from the remote data source.
     *
     * This method fetches the latest tasks from the remote data source
     * and emits them to [tasks]
     */
    suspend fun refresh(): Unit
}
