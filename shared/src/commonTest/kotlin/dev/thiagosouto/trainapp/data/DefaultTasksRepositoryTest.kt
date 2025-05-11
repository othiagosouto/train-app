package dev.thiagosouto.trainapp.data

import app.cash.turbine.test
import dev.thiagosouto.trainapp.data.local.TasksCache
import dev.thiagosouto.trainapp.domain.Task
import dev.thiagosouto.trainapp.utils.AppDispatchers
import dev.thiagosouto.trainapp.utils.TasksTestUtils
import dev.thiagosouto.trainapp.utils.createTaskService
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.assertEquals

internal class DefaultTasksRepositoryTest {

    @Test
    fun `Given Fetch successful Then emits items`() = runTest {
        val repository: TasksRepository = DefaultTasksRepository(
            taskService = createTaskService(),
            tasksCache = TasksCache(),
            dispatchers = AppDispatchers()
        )

        repository.tasks.test {
            repository.refresh()
            assertEquals(
                expected = TasksTestUtils.createTasks().tasks.map {
                    Task(
                        id = it.id,
                        trainId = it.trainId,
                        taskType = it.taskType,
                        priorityLevel = it.priorityLevel,
                        location = it.location,
                        description = it.description,
                        dueDate = LocalDate.parse(it.dueDate)
                    )
                },
                actual = awaitItem()
            )
        }
    }
}
