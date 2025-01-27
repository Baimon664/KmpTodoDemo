package org.baimon.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.baimon.kmp.data.database.TaskDatabase
import org.baimon.kmp.data.database.getDatabase
import org.baimon.kmp.data.database.mapToDomain
import org.baimon.kmp.domain.model.Task
import org.baimon.kmp.domain.model.mapToData
import org.baimon.kmp.presentation.addtaskscreen.AddTaskScreen
import org.baimon.kmp.presentation.todomainscreen.TodoMainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    taskDatabaseBuilder: RoomDatabase.Builder<TaskDatabase>
) {
    MaterialTheme {
        val navController = rememberNavController()
        val database = remember { getDatabase(taskDatabaseBuilder) }
        val viewModel: TaskDataViewModel = viewModel { TaskDataViewModel(database) }
        val taskData by viewModel.taskItem.collectAsStateWithLifecycle()

        NavHost(navController, MainScreen) {
            composable<MainScreen> {
                TodoMainScreen(
                    onNavigateToNewTask = {
                        navController.navigate(NewTask)
                    },
                    onCheck = { task, isCheck ->
                        viewModel.updateTask(task.copy(isCheck = isCheck))
                    },
                    input = taskData
                )
            }

            composable<NewTask> {
                AddTaskScreen(
                    onNavigate = { newTask ->
                        viewModel.addTask(newTask)
                        navController.popBackStack()
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Serializable
object MainScreen

@Serializable
object NewTask

class TaskDataViewModel(private val database: TaskDatabase) : ViewModel() {
    private val _taskList = MutableStateFlow(mutableListOf<Task>())
    val taskItem: StateFlow<List<Task>>
        get() = _taskList

    init {
//        _taskList.update { current ->
//            current.add(Task(id = 1, title = "Task1", description = "It Task1",isCheck = false))
//            current
//        }

        viewModelScope.launch(Dispatchers.IO) {
            database.taskDao().getAllTask().collect {
                _taskList.value = it.map { task ->
                    task.mapToDomain()
                }.toMutableList()
            }
        }
    }
    fun addTask(newItem: Task) {
//        _taskList.update { current ->
//            current.add(newItem)
//            current
//        }

        viewModelScope.launch {
            database.taskDao().insertTask(newItem.mapToData())
        }
    }
    fun updateTask(task: Task) {
//        _taskList.update { current ->
//            current.map {
//                if(it.title == task.title && it.id == task.id) {
//                    return@map task
//                }
//                return@map it
//            }.toMutableList()
//        }

        viewModelScope.launch {
            database.taskDao().updateCheckById(task.id, task.isCheck)
        }
    }
}