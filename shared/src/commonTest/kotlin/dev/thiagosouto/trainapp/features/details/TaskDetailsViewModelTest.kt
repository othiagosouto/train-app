package dev.thiagosouto.trainapp.features.details

import app.cash.turbine.test
import dev.thiagosouto.trainapp.utils.createTaskRepository
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
internal class TaskDetailsViewModelTest {

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given success loading request Then returns LoadedState`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val repository = createTaskRepository()
        val viewModel = TaskDetailsViewModel(repository)

        viewModel.uiState.test {
            assertEquals(
                expected = TaskDetailsUiState.Loading,
                actual = awaitItem()
            )
            viewModel.init("MAINT-1001")
            repository.refresh()

            assertEquals(
                expected = TaskDetailsUiState.Content(
                    labels = listOf(
                        LabelValue("Train Identifier", "TR-4872"),
                        LabelValue("Task type", "Brake Inspection"),
                        LabelValue("Priority", "High"),
                        LabelValue("Location", "Central Depot"),
                        LabelValue("Due date", "25/04/2025"),
                        LabelValue("Description", "Description 1")
                    )
                ),
                actual = awaitItem()
            )
        }
    }
}
