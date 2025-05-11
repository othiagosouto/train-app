package dev.thiagosouto.trainapp.data.di

import dev.thiagosouto.trainapp.data.TasksRemote
import dev.thiagosouto.trainapp.data.model.TasksBaseUrl
import dev.thiagosouto.trainapp.data.remote.DefaultTasksRemote
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
    single<HttpClient> {
        httpClient()
    }
    single<TasksRemote> {
        DefaultTasksRemote(
            json = get(),
            httpClient = get(),
            baseUrl = TasksBaseUrl
        )
    }
}
