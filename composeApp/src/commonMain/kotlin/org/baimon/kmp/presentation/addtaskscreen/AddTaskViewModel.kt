package org.baimon.kmp.presentation.addtaskscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.baimon.kmp.data.database.TaskDatabase
import org.baimon.kmp.data.database.TaskEntity

class AddTaskViewModel(
    private val database: TaskDatabase
) : ViewModel() {
    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow<String>("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _isInputComplete = MutableStateFlow<Boolean>(false)
    val isInputComplete: StateFlow<Boolean> = _isInputComplete.asStateFlow()

    private val _back = MutableStateFlow<Boolean>(false)
    val back: StateFlow<Boolean> = _back.asStateFlow()

    fun setTitle(newText: String) {
        _title.value = newText
    }

    fun setDescription(newText: String) {
        _description.value = newText
    }

    private fun isTitleValid(): Boolean {
        return _title.value.isNotBlank()
    }

    private fun isDescriptionValid(): Boolean {
        return _description.value.isNotBlank()
    }

    fun validateField() {
        _isInputComplete.value = isTitleValid() && isDescriptionValid()
    }

    fun updateTask() {
        viewModelScope.launch {
            flowOf(
                database.taskDao().insertTask(
                    TaskEntity(
                        title = _title.value,
                        description = _description.value,
                        isCheck = false
                    )
                )
            ).collect {
                _back.value = true
            }
        }
    }
}