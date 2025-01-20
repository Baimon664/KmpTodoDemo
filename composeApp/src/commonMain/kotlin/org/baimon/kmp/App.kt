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

        val taskDatabase = remember {
            getDatabase(taskDatabaseBuilder)
        }

        val taskRepository = remember {
            TaskRepositoryImpl(
                taskDatabase
            )
        }

        val getAllTaskUseCase = remember {
            GetAllTaskUseCase(taskRepository)
        }

        val updateCheckTaskUseCase = remember {
            UpdateCheckTaskUseCase(taskRepository)
        }

        val addTaskUseCase = remember {
            AddTaskUseCase(taskRepository)
        }

        NavHost(navController, MainScreen) {
            composable<MainScreen> {
                TodoMainScreen(
                    getAllTaskUseCase = getAllTaskUseCase,
                    updateCheckTaskUseCase = updateCheckTaskUseCase,
                    onNavigateToNewTask = {
                        navController.navigate(NewTask)
                    }
                )
            }
            composable<NewTask> {
                AddTaskScreen(
                    addTaskUseCase = addTaskUseCase,
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