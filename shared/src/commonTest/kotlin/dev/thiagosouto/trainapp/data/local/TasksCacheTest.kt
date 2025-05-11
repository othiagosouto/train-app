package dev.thiagosouto.trainapp.data.local

import app.cash.turbine.test
import dev.thiagosouto.trainapp.domain.Task
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class TasksCacheTest {

    @Test
    fun `Given Events Then validates emission`() = runTest {
        val tasksCache = TasksCache()
        tasksCache.tasks.test {
            val items = listOf(
                Task(
                    id = "1",
                    trainId = "1",
                    taskType = "type",
                    priorityLevel = "Low",
                    location = "location",
                    description = "description",
                    dueDate = LocalDate.parse("2023-10-01")
                )
            )
            tasksCache.emit(items)
            assertEquals(items, awaitItem())
        }
    }
}
