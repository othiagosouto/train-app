package dev.thiagosouto.trainapp.data.remote

import dev.thiagosouto.trainapp.data.TaskService
import dev.thiagosouto.trainapp.domain.DomainException
import dev.thiagosouto.trainapp.domain.Task
import dev.thiagosouto.trainapp.utils.TasksTestUtils
import dev.thiagosouto.trainapp.utils.createMockEngine
import dev.thiagosouto.trainapp.utils.createTaskService
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class DefaultTaskServiceTest {
    @Test
    fun `Given successful request Then returns tasks`() = runTest {
        val tasksRemote: TaskService = createTaskService()

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
            actual = tasksRemote.fetch()
        )
    }

    @Test
    fun `Given ClientRequestException request Then throws ClientException`() = runTest {
        val tasksRemote: TaskService = createTaskService(
            mockEngine = createMockEngine(
                content = "",
                statusCode = HttpStatusCode.NotFound
            )
        )

        assertFailsWith<DomainException.ClientException>(
            message = "Expecting ClientException",
            block = { tasksRemote.fetch() }
        )
    }

    @Test
    fun `Given ServerResponseException request Then throws ServerException`() = runTest {
        val tasksRemote: TaskService = createTaskService(
            mockEngine = createMockEngine(
                content = "",
                statusCode = HttpStatusCode.InternalServerError
            )
        )

        assertFailsWith<DomainException.ServerException>(
            message = "Expecting ServerException",
            block = { tasksRemote.fetch() }
        )
    }
}
