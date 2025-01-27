package org.baimon.kmp.presentation.todomainscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import kmpdemo1.composeapp.generated.resources.Res
import org.baimon.kmp.TaskData
import org.baimon.kmp.domain.task.usecase.GetAllTaskUseCase
import org.baimon.kmp.domain.task.usecase.UpdateCheckTaskUseCase
import org.baimon.kmp.presentation.widgets.TodoTaskItem
import kotlin.reflect.KProperty

var list = arrayOf("a","b","c")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoMainScreen(
    onNavigateToNewTask: () -> Unit,
    input: TaskData
) {
    var stateList by remember { mutableStateOf( list ) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Todo App")
                },
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
//                stateList += "new data"
                onNavigateToNewTask()
            }) {
                Icon(Icons.Default.Add, "add icon")
            }
        }
    ) {
        innerPadding ->
        LazyColumn (
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(input.taskList) {
                TodoTaskItem(
                    item = it,
                    onCheck = {

                    }
                )
            }
        }
    }
}
