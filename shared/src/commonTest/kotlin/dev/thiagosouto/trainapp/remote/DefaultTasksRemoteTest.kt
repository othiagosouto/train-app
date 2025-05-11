package dev.thiagosouto.trainapp.remote

import dev.thiagosouto.trainapp.data.TasksRemote
import dev.thiagosouto.trainapp.domain.Task
import dev.thiagosouto.trainapp.utils.TasksTestUtils
import dev.thiagosouto.trainapp.utils.createTasksRemote
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DefaultTasksRemoteTest {
    @Test
    fun `Given successful request Then returns tasks`() = runTest {
        val tasksRemote: TasksRemote = createTasksRemote()

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
}
