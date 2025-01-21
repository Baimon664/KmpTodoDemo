package org.baimon.kmp.presentation.addtaskscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.baimon.kmp.database.TaskDatabase
import org.baimon.kmp.model.Task
import org.baimon.kmp.model.mapToEntity

class AddTaskViewModel(
    private val taskDatabase: TaskDatabase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AddTaskScreenUiState(
            title = "",
            description = "",
            isInputComplete = false,
            backToMainScreen = false
        )
    )
    val uiState: StateFlow<AddTaskScreenUiState>
        get() = _uiState

    fun setTitle(newText: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = newText
            )
        }
        validateField()
    }

    fun setDescription(newText: String) {
        _uiState.update { currentState ->
            currentState.copy(
                description = newText
            )
        }
        validateField()
    }

    private fun isTitleValid(): Boolean {
        return _uiState.value.title.isNotBlank()
    }

    private fun isDescriptionValid(): Boolean {
        return _uiState.value.description.isNotBlank()
    }

    private fun validateField() {
        _uiState.update { currentState ->
            currentState.copy(
                isInputComplete = isTitleValid() && isDescriptionValid()
            )
        }
    }

    fun updateTask() {
        viewModelScope.launch {
            flowOf(
                taskDatabase.taskDao().insertTask(
                    Task(
                        title = _uiState.value.title,
                        description = _uiState.value.description,
                        isCheck = false
                    ).mapToEntity()
                )
            ).catch {

            }.collect {
                _uiState.update { currentState ->
                    currentState.copy(
                        backToMainScreen = true
                    )
                }
            }
        }
    }
}