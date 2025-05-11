package dev.thiagosouto.trainapp.data.di

import dev.thiagosouto.trainapp.data.DefaultTasksRepository
import dev.thiagosouto.trainapp.data.TaskService
import dev.thiagosouto.trainapp.data.TasksRepository
import dev.thiagosouto.trainapp.data.local.TasksCache
import dev.thiagosouto.trainapp.data.model.BaseUrl
import dev.thiagosouto.trainapp.data.model.TasksBaseUrl
import dev.thiagosouto.trainapp.data.remote.DefaultTaskService
import dev.thiagosouto.trainapp.data.remote.serviceResponseValidator
import dev.thiagosouto.trainapp.utils.AppDispatchers
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val urlModule = {
    module {
        factory<BaseUrl> {
            TasksBaseUrl
        }
    }
}

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
    factory<TaskService> {
        DefaultTaskService(
            json = get(),
            httpClient = get(),
            baseUrl = get<BaseUrl>()
        )
    }
    single<TasksCache> {
        TasksCache()
    }

    factory<AppDispatchers> {
        AppDispatchers()
    }

    factory<TasksRepository> {
        DefaultTasksRepository(
            taskService = get(),
            tasksCache = get(),
            dispatchers = get<AppDispatchers>()
        )
    }
}
