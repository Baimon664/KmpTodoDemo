package org.baimon.kmp.presentation.todomainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.baimon.kmp.domain.task.model.Task
import org.baimon.kmp.domain.task.usecase.GetAllTaskUseCase
import org.baimon.kmp.domain.task.usecase.UpdateCheckTaskUseCase

class TodoMainViewModel(
    private val getAllTaskUseCase: GetAllTaskUseCase,
    private val updateCheckTaskUseCase: UpdateCheckTaskUseCase
) : ViewModel() {

    private var _todoList = MutableStateFlow(listOf<Task>())
    val todoList: StateFlow<List<Task>> = _todoList.asStateFlow()

    private var _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun getTodoList() {
        viewModelScope.launch {
            getAllTaskUseCase.execute(Unit)
                .onStart {
                    _loading.value = true
                }
                .onCompletion {
                    _loading.value = false
                }
                .collect {
                    _todoList.value = it
                }
        }
    }

    fun check(id: Int, isCheck: Boolean) {
        viewModelScope.launch {
            updateCheckTaskUseCase.execute(Pair(id, isCheck)).collect {
                val current = _todoList.value.first { it.id == id }
                val expect = current.copy(isCheck = isCheck)
                _todoList.value = _todoList.value.map {
                    if (it.id == id) expect else it
                }
            }
        }
    }
}