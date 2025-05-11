package dev.thiagosouto.trainapp.fakes

import dev.thiagosouto.trainapp.data.TasksRepository
import dev.thiagosouto.trainapp.data.local.TasksCache
import dev.thiagosouto.trainapp.domain.DomainException
import dev.thiagosouto.trainapp.domain.Task
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.datetime.LocalDate

internal class TaskRepositoryFake(var hasError: Boolean) : TasksRepository {
    private val cache = TasksCache()
    override val tasks: SharedFlow<List<Task>>
        get() = cache.tasks

    override suspend fun refresh() {
        if (hasError) {
            throw DomainException.UnhandledException(Exception())
        }
        val data = listOf(Task("", "", "Task 1", "", "", LocalDate.parse("2023-01-01"), ""))
        cache.emit(data)
    }
}
