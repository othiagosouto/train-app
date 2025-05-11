package dev.thiagosouto.trainapp.data

import dev.thiagosouto.trainapp.data.local.TasksCache
import dev.thiagosouto.trainapp.domain.Task
import dev.thiagosouto.trainapp.utils.AppDispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

class DefaultTasksRepository(
    private val taskService: TaskService,
    private val tasksCache: TasksCache,
    private val dispatchers: AppDispatchers
) : TasksRepository {

    override val tasks: SharedFlow<List<Task>>
        get() = tasksCache.tasks

    override suspend fun refresh() {
        withContext(dispatchers.io) {
            taskService.fetch()
                .let { items -> tasksCache.emit(items) }
        }
    }
}
