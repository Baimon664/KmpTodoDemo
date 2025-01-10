package org.baimon.kmp.domain.task

import kotlinx.coroutines.flow.Flow
import org.baimon.kmp.domain.task.model.Task

interface TaskRepository {
    suspend fun getAllTask(): Flow<List<Task>>

    suspend fun updateCheckById(id: Int, check: Boolean): Flow<Unit>

    suspend fun insertTask(task: Task): Flow<Unit>
}