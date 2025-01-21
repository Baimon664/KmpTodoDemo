package org.baimon.kmp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.baimon.kmp.model.Task

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val isCheck: Boolean
)

fun TaskEntity.mapToModel(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCheck = isCheck
    )
}
