package org.baimon.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.RoomDatabase
import kotlinx.serialization.Serializable
import org.baimon.kmp.data.database.TaskDatabase
import org.baimon.kmp.data.database.getDatabase
import org.baimon.kmp.data.task.TaskRepositoryImpl
import org.baimon.kmp.domain.task.model.Task
import org.baimon.kmp.domain.task.usecase.AddTaskUseCase
import org.baimon.kmp.domain.task.usecase.GetAllTaskUseCase
import org.baimon.kmp.domain.task.usecase.UpdateCheckTaskUseCase
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


class TaskData {
    var taskList: ArrayList<TaskItem> = arrayListOf()
    init {
        taskList.add( TaskItem(title = "Task1", description = "It Task1",isCheck = false) )
    }
    fun addTask(newItem: TaskItem) {
        taskList.add(newItem)
    }
}
data class TaskItem (
    val id: String = "1",
    val title: String,
    val description: String,
    var isCheck: Boolean
)