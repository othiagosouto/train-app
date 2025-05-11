package dev.thiagosouto.trainapp.utils

import dev.thiagosouto.trainapp.data.TasksRemote
import dev.thiagosouto.trainapp.data.model.BaseUrl
import dev.thiagosouto.trainapp.data.remote.DefaultTasksRemote
import dev.thiagosouto.trainapp.data.remote.serviceResponseValidator
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.test.fail

const val TasksUrl =
    "https://gist.githubusercontent.com/mootazltaief/6f6ae202071449386b57eb3876ce25ee/raw" +
            "/dfa58b73b5fea4951276f89e09b4267d81c0895b/tasks.kt"

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

fun createMockEngine(
    content: String,
    statusCode: HttpStatusCode = HttpStatusCode.OK,
    url: String = TasksUrl
) =
    MockEngine { request ->
        if (request.url.toString() != url) {
            fail("Unexpected request URL: ${request.url}")
        }
        respond(
            content = content,
            status = statusCode,
            headers = headersOf("Content-Type", "text/plain; charset=utf-8")
        )
    }

fun createTasksRemote(
    mockEngine: MockEngine = createMockEngine(
        content = TasksTestUtils.createTasksJson(),
        statusCode = HttpStatusCode.OK
    ),
    baseUrl: BaseUrl = "https://gist.githubusercontent.com"
): TasksRemote = DefaultTasksRemote(
    json = json,
    httpClient = HttpClient(mockEngine)
    {
        install(ContentNegotiation) {
            json(json = json)
        }
        serviceResponseValidator()
    },
    baseUrl = baseUrl
)
