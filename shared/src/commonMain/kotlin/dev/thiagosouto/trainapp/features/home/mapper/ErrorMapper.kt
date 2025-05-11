package dev.thiagosouto.trainapp.features.home.mapper

import dev.thiagosouto.trainapp.components.error.ErrorUiModel
import dev.thiagosouto.trainapp.domain.DomainException

internal class ErrorMapper {
    fun apply(exception: DomainException): ErrorUiModel = when (exception) {
        is DomainException.ServerException -> ErrorUiModel(
            "Server Error",
            "Server failed. Please try again later."
        )

        is DomainException.ClientException -> ErrorUiModel(
            "Client Error",
            "Operation failed. Please seek support."
        )

        is DomainException.NetworkException -> ErrorUiModel(
            "Network Error",
            "Internet not available. Please check your connection."
        )

        is DomainException.UnhandledException -> ErrorUiModel(
            "Unhandled Error",
            "An unexpected error occurred. Please try again."
        )
    }
}
