package org.baimon.kmp.presentation.todomainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.baimon.kmp.domain.model.Task
import org.baimon.kmp.presentation.widgets.TodoTaskItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoMainScreen(
    onNavigateToNewTask: () -> Unit,
    onCheck: (newTask: Task, isCheck: Boolean) -> Unit,
    input: List<Task>
) {
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
                onNavigateToNewTask()
            }) {
                Icon(Icons.Default.Add, "add icon")
            }
        }
    ) {
        innerPadding ->
        LazyColumn (
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 60.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(input) {
                TodoTaskItem(
                    item = it,
                    onCheck = { task, isCheck ->
                        onCheck(task, isCheck)
                    }
                )
            }
        }
    }
}
