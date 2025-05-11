package dev.thiagosouto.trainapp.features.home.mapper

import dev.thiagosouto.trainapp.components.error.ErrorUiModel
import dev.thiagosouto.trainapp.domain.DomainException
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ErrorMapperTest {
    private val mapper = ErrorMapper()

    @Test
    fun `Given ServerException Then return ErrorUiModel`() {
        assertEquals(
            expected = ErrorUiModel(
                "Server Error",
                "Server failed. Please try again later."
            ),
            actual = mapper.apply(DomainException.ServerException(500, Exception()))
        )
    }

    @Test
    fun `Given ClientException Then return ErrorUiModel`() {
        assertEquals(
            expected = ErrorUiModel(
                "Client Error",
                "Operation failed. Please seek support."
            ),
            actual = mapper.apply(DomainException.ClientException(444, Exception()))
        )
    }

    @Test
    fun `Given NetworkException Then return ErrorUiModel`() {
        assertEquals(
            expected = ErrorUiModel(
                "Network Error",
                "Internet not available. Please check your connection."
            ),
            actual = mapper.apply(DomainException.NetworkException(Exception()))
        )
    }

    @Test
    fun `Given UnhandledException Then return ErrorUiModel`() {
        assertEquals(
            expected = ErrorUiModel(
                "Unhandled Error",
                "An unexpected error occurred. Please try again."
            ),
            actual = mapper.apply(DomainException.UnhandledException(Exception()))
        )
    }
}
