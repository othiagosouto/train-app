package dev.thiagosouto.trainapp.features.home

import app.cash.turbine.test
import dev.thiagosouto.trainapp.components.error.ErrorUiModel
import dev.thiagosouto.trainapp.features.home.mapper.ErrorMapper
import dev.thiagosouto.trainapp.utils.TasksTestUtils
import dev.thiagosouto.trainapp.utils.createTaskRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class HomeViewModelTest {

    @Test
    fun `Given error loading request Then returns ErrorState`() = runTest {
        val viewModel = HomeViewModel(createTaskRepository(HttpStatusCode.NotFound), ErrorMapper())

        viewModel.uiState.test {
            viewModel.load()

            assertEquals(
                expected = HomeState.Idle,
                actual = awaitItem()
            )

            assertEquals(
                expected = HomeState.Loading,
                actual = awaitItem()
            )
            assertEquals(
                expected = HomeState.Error(
                    error = ErrorUiModel(
                        "Client Error",
                        "Operation failed. Please seek support."
                    )
                ),
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `Given success loading request Then returns LoadedState`() = runTest {
        val viewModel = HomeViewModel(createTaskRepository(), ErrorMapper())

        viewModel.uiState.test {
            viewModel.load()

            assertEquals(
                expected = HomeState.Idle,
                actual = awaitItem()
            )

            assertEquals(
                expected = HomeState.Loading,
                actual = awaitItem()
            )
            assertEquals(
                expected = HomeState.Content(
                    loading = false,
                    title = "Maintenance Tasks",
                    error = null,
                    tasks = TasksTestUtils.createTasks().tasks.map { task ->
                        TaskItemUiModel(
                            id = task.id,
                            headline = "Train ${task.trainId} - ${task.taskType}",
                            supportingText = "Priority: ${task.priorityLevel}"
                        )
                    }
                ),
                actual = awaitItem()
            )
        }
    }
}
