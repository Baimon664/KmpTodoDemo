package org.baimon.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import org.baimon.kmp.presentation.AddTaskScreen
import org.baimon.kmp.presentation.TodoMainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
) {
    MaterialTheme {
        val taskData = viewModel { TaskData() }
        val navControl = rememberNavController()
        NavHost( navControl , Mainscreen ) {
            composable<Mainscreen> {
                TodoMainScreen(
                    taskData = taskData,
                    onAddTask = {
                        navControl.navigate(NewTask)
                    },
                    onCheck = {
                        taskData.updateTask( it )
                    }
                )
            }
            composable<NewTask> {
                AddTaskScreen( onNavigate = { newItem ->
                    taskData.addTask(newItem)
//                    navControl.popBackStack()
                    navControl.navigateUp()
                }, onBack = {
//                    navControl.popBackStack()
                    navControl.navigateUp()
                } )
            }
        }
    }
}

@Serializable
object Mainscreen

@Serializable
object NewTask

class TaskData: ViewModel() {
    var tableItem: MutableList<TaskItem> = mutableListOf()
    fun addTask(newTask: TaskItem) {
        tableItem.add(newTask)
    }
    fun updateTask(task: TaskItem) {
        //Update database
    }
}

data class TaskItem (
    var title: String,
    var description: String,
    var isCheck: Boolean
)