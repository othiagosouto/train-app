package dev.thiagosouto.trainapp.domain

import kotlinx.datetime.LocalDate

/**
 * Represents a task to be executed by a train worker
 *
 * @property id Unique identifier of the task.
 * @property trainId Identifier of the train associated with this task.
 * @property taskType Type of task to be performed (e.g., inspection, maintenance).
 * @property priorityLevel Indicates the urgency or importance of the task (e.g., High, Medium, Low).
 * @property location Physical or logical location where the task is to be executed.
 * @property dueDate Deadline or expected completion date for the task, formatted as a string.
 * @property description Additional details or instructions about the task.
 */
data class Task(
    val id: String,
    val trainId: String,
    val taskType: String,
    val priorityLevel: String,
    val location: String,
    val dueDate: LocalDate,
    val description: String
)
