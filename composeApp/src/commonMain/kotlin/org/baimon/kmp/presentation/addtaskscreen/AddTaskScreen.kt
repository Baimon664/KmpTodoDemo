package org.baimon.kmp.presentation.addtaskscreen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.baimon.kmp.data.database.TaskDatabase
import org.baimon.kmp.domain.task.usecase.AddTaskUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    addTaskUseCase: AddTaskUseCase,
    onBack: () -> Unit,
) {

    val viewModel: AddTaskViewModel = viewModel { AddTaskViewModel(addTaskUseCase) }
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val isInputComplete by viewModel.isInputComplete.collectAsState()
    val back by viewModel.back.collectAsState()
    val focusManager = LocalFocusManager.current

    if(back) {
        onBack()
    }

    LaunchedEffect(title, description) {
        viewModel.validateField()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "arrow back")
                    }
                },
                title = {
                    Text(
                        "Add new task",
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { text ->
                    viewModel.setTitle(text)
                },
                label = {
                    Text(text = "Title")
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = { text ->
                    viewModel.setDescription(text)
                },
                label = {
                    Text(text = "Description")
                }
            )

            ElevatedButton(
                onClick = {
                    viewModel.updateTask()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isInputComplete
            ) {
                Text(text = "Save")
            }
        }
    }
}