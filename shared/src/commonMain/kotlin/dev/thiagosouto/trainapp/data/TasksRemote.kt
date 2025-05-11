package dev.thiagosouto.trainapp.data

import dev.thiagosouto.trainapp.domain.Task

/**
 * Fetch tasks from remote data source
 */
fun interface TasksRemote {

    suspend fun fetch(): List<Task>
}
