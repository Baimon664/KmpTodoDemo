package org.baimon.kmp.presentation.addtaskscreen

data class AddTaskScreenUiState(
    val title: String,
    val description: String,
    val isInputComplete: Boolean,
    val backToMainScreen: Boolean
)