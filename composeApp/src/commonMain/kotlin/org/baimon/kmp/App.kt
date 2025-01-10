package org.baimon.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.baimon.kmp.data.database.TaskDatabase
import org.baimon.kmp.presentation.addtaskscreen.AddTaskScreen
import org.baimon.kmp.presentation.todomainscreen.TodoMainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    taskDatabase: TaskDatabase
) {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController, MainScreen) {
            composable<MainScreen> {
                TodoMainScreen(
                    database = taskDatabase,
                    onNavigateToNewTask = {
                        navController.navigate(NewTask)
                    }
                )
            }
            composable<NewTask> {
                AddTaskScreen(
                    database = taskDatabase,
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