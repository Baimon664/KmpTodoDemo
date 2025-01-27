package org.baimon.kmp.model

import org.baimon.kmp.database.TaskEntity

data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val isCheck: Boolean,
)

fun Task.mapToEntity(): TaskEntity {
    return TaskEntity(
        title = title,
        description = description,
        isCheck = isCheck
    )
}
