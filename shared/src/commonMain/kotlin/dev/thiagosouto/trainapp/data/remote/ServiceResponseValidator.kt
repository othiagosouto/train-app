package dev.thiagosouto.trainapp.data.remote

import dev.thiagosouto.trainapp.domain.DomainException
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException

/**
 * Validates exception responses executed from HttpClient and map into domain exceptions
 */
fun HttpClientConfig<*>.serviceResponseValidator() {
    expectSuccess = true
    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            val result = when (exception) {
                is ClientRequestException -> DomainException.ClientException(
                    exception.response.status.value,
                    exception
                )

                is ServerResponseException -> DomainException.ServerException(
                    exception.response.status.value,
                    exception
                )

                is IOException -> DomainException.NetworkException(exception)

                else -> DomainException.UnhandledException(exception)
            }
            throw result
        }
    }
}
