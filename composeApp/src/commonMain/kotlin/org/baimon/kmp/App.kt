package org.baimon.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.RoomDatabase
import kotlinx.serialization.Serializable
import org.baimon.kmp.database.TaskDatabase
import org.baimon.kmp.database.getDatabase
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
        val taskData: TaskData = TaskData()

        NavHost(navController, MainScreen) {

            composable<MainScreen> {
                TodoMainScreen(
                    onNavigateToNewTask = {
                        navController.navigate(NewTask)
                    },
                    onCheck = { },
                    input = taskData
                )
            }

            composable<NewTask> {
                AddTaskScreen(
                    onNavigate = { newTask ->
                        taskData.addTask(newTask)
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

class TaskData : ViewModel() {
    var taskList: ArrayList<TaskItem> = arrayListOf()
    init {
        taskList.add( TaskItem(title = "Task1", description = "It Task1",isCheck = false) )
    }
    fun addTask(newItem: TaskItem) {
        taskList.add(newItem)
    }
    fun updateTask(task: TaskItem) {
        TODO()
    }
}
data class TaskItem (
    val id: String = "1",
    val title: String,
    val description: String,
    var isCheck: Boolean
)