package dev.thiagosouto.trainapp.features.home

import app.cash.turbine.test
import dev.thiagosouto.trainapp.components.error.ErrorUiModel
import dev.thiagosouto.trainapp.features.home.mapper.ErrorMapper
import dev.thiagosouto.trainapp.utils.TasksTestUtils
import dev.thiagosouto.trainapp.utils.createTaskRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeViewModelTest {

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given error loading request Then returns ErrorState`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val viewModel = HomeViewModel(createTaskRepository(HttpStatusCode.NotFound), ErrorMapper())

        viewModel.uiState.test {
            viewModel.load()
            assertEquals(
                expected = listOf(
                    HomeState.Idle, HomeState.Loading, HomeState.Error(
                        error = ErrorUiModel(
                            "Client Error",
                            "Operation failed. Please seek support."
                        )
                    )
                ),
                actual = listOf(awaitItem(), awaitItem(), awaitItem())
            )
        }
    }

    @Test
    fun `Given success loading request Then returns LoadedState`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val viewModel = HomeViewModel(createTaskRepository(), ErrorMapper())

        viewModel.uiState.test {
            viewModel.load()
            assertEquals(
                expected = listOf(
                    HomeState.Idle, HomeState.Loading, HomeState.Content(
                        tasks = TasksTestUtils.createTasks().tasks.map { task ->
                            TaskItemUiModel(
                                id = task.id,
                                headline = "Train ${task.trainId} - ${task.taskType}",
                                supportingText = "Priority: ${task.priorityLevel}"
                            )
                        }
                    )),
                actual = listOf(awaitItem(), awaitItem(), awaitItem())
            )
        }
    }
}
