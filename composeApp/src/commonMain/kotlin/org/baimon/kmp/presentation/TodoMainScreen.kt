package org.baimon.kmp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.baimon.kmp.TaskData
import org.baimon.kmp.TaskItem

var tableItem = listOf("a","b","c")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoMainScreen(
    taskData: TaskData,
    onAddTask: () -> Unit,
    onCheck: (TaskItem) -> Unit
) {
    var stateList by remember { mutableStateOf(tableItem) }
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
                onAddTask()
            }) {
                Icon(Icons.Default.Add, "add icon")
            }
        }
    )
    {
        innerPadder ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding( innerPadder ).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy( 16.dp )
        ) {
            items( taskData.tableItem ) {
                TodoCardItem(it, onCheck = onCheck)
            }
        }
    }
}