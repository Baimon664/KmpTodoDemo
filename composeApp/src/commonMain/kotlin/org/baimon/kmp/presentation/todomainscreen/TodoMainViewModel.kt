package org.baimon.kmp.presentation.todomainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.baimon.kmp.data.database.TaskDatabase
import org.baimon.kmp.data.database.TaskEntity

class TodoMainViewModel(
    private val database: TaskDatabase
) : ViewModel() {

    private var _todoList = MutableStateFlow(listOf<Todo>())
    val todoList: StateFlow<List<Todo>> = _todoList.asStateFlow()

    private var _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun getTodoList() {
        viewModelScope.launch {
//            val temp = mutableListOf<Todo>()
//            for (i in 1..10) {
//                temp.add(
//                    Todo(
//                        id = i.toString(),
//                        title = "Title $i",
//                        description = "Description $i",
//                        isCheck = false
//                    )
//                )
//            }
//
//            flowOf(temp).onStart {
//                _loading.value = true
//                delay(1000L)
//            }.onCompletion {
//                _loading.value = false
//            }.collect {
//                _todoList.value = it
//            }
            flowOf(
                database.taskDao().getAllTask()
            ).onStart {
                _loading.value = true
            }.onCompletion {
                _loading.value = false
            }.collect {
                _todoList.value = it.map {
                    it.toTodo()
                }
            }
        }
    }

    fun check(id: Int, isCheck: Boolean) {
        val current = _todoList.value.first { it.id == id }
        val expect = current.copy(isCheck = isCheck)
        _todoList.value = _todoList.value.map {
            if (it.id == id) expect else it
        }
        viewModelScope.launch {
            database.taskDao().updateCheckById(id, isCheck)
        }
    }

    private fun TaskEntity.toTodo(): Todo {
        return Todo(
            id = this.id,
            title = this.title,
            description = this.description,
            isCheck = this.isCheck
        )
    }
}

data class Todo(
    val id: Int,
    val title: String,
    val description: String,
    val isCheck: Boolean,
)