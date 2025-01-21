package org.baimon.kmp.presentation.todomainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.baimon.kmp.database.TaskDatabase
import org.baimon.kmp.database.mapToModel

class TodoMainViewModel(
    private val taskDatabase: TaskDatabase
) : ViewModel() {

    private var _uiState = MutableStateFlow(
        TodoMainScreenUiState(
            isLoading = false,
            taskList = listOf()
        )
    )
    val uiState: StateFlow<TodoMainScreenUiState>
        get() = _uiState

    fun getTodoList() {
        viewModelScope.launch {
            flowOf(taskDatabase.taskDao().getAllTask())
                .map {
                    it.map { it.mapToModel() }
                }.onStart {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = true
                        )
                    }
                    delay(500L)
                }.onCompletion {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false
                        )
                    }
                }.catch {

                }.collect {
                    _uiState.update { currentState ->
                        currentState.copy(
                            taskList = it
                        )
                    }
                }
        }
    }

    fun check(id: Int, isCheck: Boolean) {
        viewModelScope.launch {
            flowOf(taskDatabase.taskDao().updateCheckById(id, isCheck))
                .catch {

                }.collect {
                    _uiState.update { currentState ->
                        val current = currentState.taskList.first { it.id == id }
                        val expect = current.copy(isCheck = isCheck)
                        currentState.copy(
                            taskList = currentState.taskList.map {
                                if (it.id == id) expect else it
                            }
                        )
                    }
                }
        }
    }
}