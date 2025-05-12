package dev.thiagosouto.trainapp.features.details

import app.cash.turbine.test
import dev.thiagosouto.trainapp.utils.createTaskRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TaskDetailsViewModelTest {

    @Test
    fun `Given success loading request Then returns LoadedState`() = runTest {
        val repository = createTaskRepository()
        val viewModel = TaskDetailsViewModel(repository)
        viewModel.init("MAINT-1001")
        repository.refresh()
        viewModel.uiState.test {
            assertEquals(
                expected = TaskDetailsUiState.Loading,
                actual = awaitItem()
            )

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
