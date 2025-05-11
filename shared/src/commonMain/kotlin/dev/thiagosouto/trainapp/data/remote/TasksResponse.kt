package dev.thiagosouto.trainapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TasksResponse(@SerialName("tasks") val tasks: List<Task>) {
    
    @Serializable
    data class Task(
        @SerialName("taskId") val id: String,
        @SerialName("trainId") val trainId: String,
        @SerialName("taskType") val taskType: String,
        @SerialName("priorityLevel") val priorityLevel: String,
        @SerialName("location") val location: String,
        @SerialName("dueDate") val dueDate: String,
        @SerialName("description") val description: String
    )
}
