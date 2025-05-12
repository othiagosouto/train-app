package dev.thiagosouto.trainapp.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.thiagosouto.trainapp.data.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

internal class TaskDetailsViewModel(private val repository: TasksRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskDetailsUiState>(TaskDetailsUiState.Loading)
    val uiState: StateFlow<TaskDetailsUiState> = _uiState

    fun init(taskId: String) {
        viewModelScope.launch {
            repository.tasks
                .collect { tasks ->
                    val task = tasks.first { it.id == taskId }
                    val dateTimeFormat = LocalDate.Format {
                        @OptIn(FormatStringsInDatetimeFormats::class)
                        byUnicodePattern("dd/MM/yyyy")
                    }
                    _uiState.value = TaskDetailsUiState.Content(
                        labels = buildList {
                            add(LabelValue("Train Identifier", task.trainId))
                            add(LabelValue("Task type", task.taskType))
                            add(LabelValue("Priority", task.priorityLevel))
                            add(LabelValue("Location", task.location))
                            add(LabelValue("Due date", task.dueDate.format(dateTimeFormat)))
                            add(LabelValue("Description", task.description))
                        }
                    )
                }
        }
    }
}

sealed class TaskDetailsUiState {
    data object Loading : TaskDetailsUiState()
    data class Content(
        val labels: List<LabelValue> = emptyList(),
    ) : TaskDetailsUiState()
}

data class LabelValue(
    val label: String,
    val value: String
)
