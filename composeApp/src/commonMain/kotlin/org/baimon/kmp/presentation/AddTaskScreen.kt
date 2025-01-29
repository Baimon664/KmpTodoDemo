package org.baimon.kmp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.baimon.kmp.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen (
    onNavigate: (TaskItem) -> Unit,
    onBack: () -> Unit
) {
    var title by remember {  mutableStateOf("") }
    var description by remember {  mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Task")
                },
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = MaterialTheme.colorScheme.primaryContainer),
                navigationIcon = {
                    IconButton(
                        onClick = { onBack() }
                    ){
                        Icon( Icons.Default.ArrowBack, "Back" )
                    }
                }

            )
        }
    )
    {
        innerPaddind ->

        Column (
            modifier = Modifier.padding(innerPaddind).padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            OutlinedTextField( modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { text -> title = text },
                label = {
                    Text("Title")
                })
            OutlinedTextField( modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = { text -> description = text },
                label = {
                    Text("Description")
                })

            ElevatedButton(
                onClick = {
                    onNavigate( TaskItem(title = title, description = description, isCheck = false) )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }


    }
}
