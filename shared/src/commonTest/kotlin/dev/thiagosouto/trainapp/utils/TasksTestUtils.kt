package dev.thiagosouto.trainapp.utils

import dev.thiagosouto.trainapp.data.remote.TasksResponse

object TasksTestUtils {
    private val task = TasksResponse.Task(
        id = "MAINT-1001",
        trainId = "TR-4872",
        taskType = "Brake Inspection",
        priorityLevel = "High",
        location = "Central Depot",
        description = "Description 1",
        dueDate = "2025-04-25"
    )

    fun createTasks(): TasksResponse =
        TasksResponse(
            tasks = listOf(
                task,
                task.copy(priorityLevel = "Medium"),
                task.copy(priorityLevel = "Low"),
            )
        )

    fun createTasksJson(): String =
        json.encodeToString(TasksResponse.serializer(), createTasks())
}
