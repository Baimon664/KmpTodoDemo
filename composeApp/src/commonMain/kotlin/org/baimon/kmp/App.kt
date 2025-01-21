package org.baimon.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
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

        val taskDatabase = remember {
            getDatabase(taskDatabaseBuilder)
        }

        NavHost(navController, MainScreen) {
            composable<MainScreen> {
                TodoMainScreen(
                    taskDatabase = taskDatabase,
                    onNavigateToNewTask = {
                        navController.navigate(NewTask)
                    }
                )
            }
            composable<NewTask> {
                AddTaskScreen(
                    taskDatabase = taskDatabase,
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