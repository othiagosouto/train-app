package dev.thiagosouto.trainapp.data.di

import dev.thiagosouto.trainapp.data.TasksRemote
import dev.thiagosouto.trainapp.data.model.TasksBaseUrl
import dev.thiagosouto.trainapp.data.remote.DefaultTasksRemote
import dev.thiagosouto.trainapp.data.remote.serviceResponseValidator
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
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
        httpClient {
            install(ContentNegotiation) {
                json(json = get<Json>())
            }
            serviceResponseValidator()
        }
    }
    single<TasksRemote> {
        DefaultTasksRemote(
            json = get(),
            httpClient = get(),
            baseUrl = TasksBaseUrl
        )
    }
}
