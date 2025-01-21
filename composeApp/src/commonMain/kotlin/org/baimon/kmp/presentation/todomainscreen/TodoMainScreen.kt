package org.baimon.kmp.presentation.todomainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import org.baimon.kmp.database.TaskDatabase
import org.baimon.kmp.presentation.widgets.TodoTaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoMainScreen(
    taskDatabase: TaskDatabase,
    onNavigateToNewTask: () -> Unit
) {

    val viewModel: TodoMainViewModel =
        viewModel { TodoMainViewModel(taskDatabase) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        viewModel.getTodoList()
    }

    if (uiState.isLoading) {
        Dialog(
            onDismissRequest = {}
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.Cyan)
            }
        }
    }

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
            FloatingActionButton(onClick = onNavigateToNewTask) {
                Icon(Icons.Default.Add, "add icon")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 60.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Text(text = "Todo Task", style = MaterialTheme.typography.titleLarge)
            }
            items(uiState.taskList, key = { todo -> todo.id ?: todo.title }) {
                TodoTaskItem(
                    isCheck = it.isCheck,
                    titleText = it.title,
                    descriptionText = it.description,
                    onCheckChange = { check ->
                        viewModel.check(it.id ?: 0, check)
                    },
                )
            }
        }
    }
}