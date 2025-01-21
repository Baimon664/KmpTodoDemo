package org.baimon.kmp.presentation.todomainscreen

import org.baimon.kmp.model.Task

data class TodoMainScreenUiState(
    val isLoading: Boolean,
    val taskList: List<Task>
)
