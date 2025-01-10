package org.baimon.kmp.domain.task.model

import org.baimon.kmp.data.database.TaskEntity

data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val isCheck: Boolean,
)

fun Task.mapToData(): TaskEntity {
    return TaskEntity(
        title = title,
        description = description,
        isCheck = isCheck
    )
}
