package dev.thiagosouto.trainapp.components.connectivity

import app.cash.turbine.test
import dev.jordond.connectivity.Connectivity
import dev.thiagosouto.trainapp.fakes.ConnectivityFake
import dev.thiagosouto.trainapp.fakes.TaskRepositoryFake
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ConnectivityViewModelTest {

    private val noInternetError = Error(
        hasError = true,
        message = "No internet connection, offline mode"
    )

    @Test
    fun `Given no internet Then Emits error`() = runTest {
        val fakeRepositoryFake = TaskRepositoryFake(false)
        val connectivityFake = ConnectivityFake(Connectivity.Status.Disconnected)
        val viewModel = ConnectivityViewModel(connectivityFake, fakeRepositoryFake)
        viewModel.hasError.zip(viewModel.error, ::Error).test {
            assertEquals(
                expected = noInternetError,
                actual = awaitItem()
            )
            connectivityFake.emitStatus(Connectivity.Status.Connected(false))
            assertEquals(
                expected = Error(
                    hasError = false,
                    message = ""
                ),
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `Given failed fetching data Then Emits until limit of retry`() = runTest {
        val fakeRepositoryFake = TaskRepositoryFake(true)
        val connectivityFake = ConnectivityFake(Connectivity.Status.Disconnected)
        val viewModel = ConnectivityViewModel(connectivityFake, fakeRepositoryFake)
        viewModel.error.test {
            connectivityFake.emitStatus(Connectivity.Status.Connected(true))

            assertEquals(
                expected = listOf(
                    noInternetError.message,
                    "Failed to automatically fetch data, trying again",
                    "Failed to automatically fetch data many times, please check your connection"
                ),
                actual = listOf(awaitItem(), awaitItem(), awaitItem())
            )
        }
    }

    private data class Error(
        val hasError: Boolean,
        val message: String
    )
}
