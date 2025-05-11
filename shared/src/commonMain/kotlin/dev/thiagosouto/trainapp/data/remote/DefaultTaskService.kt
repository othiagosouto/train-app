package dev.thiagosouto.trainapp.data.remote

import dev.thiagosouto.trainapp.data.TaskService
import dev.thiagosouto.trainapp.data.model.BaseUrl
import dev.thiagosouto.trainapp.domain.Task
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json

class DefaultTaskService(
    private val httpClient: HttpClient,
    private val baseUrl: BaseUrl,
    private val json: Json
) : TaskService {
    private val serviceUrl: String
        get() = "$baseUrl/mootazltaief/6f6ae202071449386b57eb3876ce25ee/raw" +
                "/dfa58b73b5fea4951276f89e09b4267d81c0895b/tasks.kt"

    override suspend fun fetch(): List<Task> {
        // Github return it as plain text since this is not a REST API, and because of that we can't
        // use the ktor CIO for deserialization.
        val response = httpClient.get(serviceUrl).bodyAsText()
        return json.decodeFromString<TasksResponse>(response).tasks.map {
            Task(
                id = it.id,
                trainId = it.trainId,
                taskType = it.taskType,
                priorityLevel = it.priorityLevel,
                location = it.location,
                description = it.description,
                dueDate = LocalDate.parse(it.dueDate)
            )
        }
    }
}
