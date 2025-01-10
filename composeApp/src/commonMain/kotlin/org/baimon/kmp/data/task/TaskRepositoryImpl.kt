package org.baimon.kmp.data.task

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.baimon.kmp.data.database.TaskDatabase
import org.baimon.kmp.data.database.mapToDomain
import org.baimon.kmp.domain.task.TaskRepository
import org.baimon.kmp.domain.task.model.Task
import org.baimon.kmp.domain.task.model.mapToData

class TaskRepositoryImpl(
    private val database: TaskDatabase
) : TaskRepository {
    override suspend fun getAllTask(): Flow<List<Task>> {
        return flowOf(
            database.taskDao().getAllTask()
        ).map { taskList ->
            taskList.map { it.mapToDomain() }
        }
    }

    override suspend fun updateCheckById(id: Int, check: Boolean): Flow<Unit> {
        return flowOf(
            database.taskDao().updateCheckById(id, check)
        )
    }

    override suspend fun insertTask(task: Task): Flow<Unit> {
        return flowOf(
            database.taskDao().insertTask(task.mapToData())
        )
    }
}