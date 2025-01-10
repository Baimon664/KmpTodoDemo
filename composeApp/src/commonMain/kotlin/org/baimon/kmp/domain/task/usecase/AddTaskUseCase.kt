package org.baimon.kmp.domain.task.usecase

import kotlinx.coroutines.flow.Flow
import org.baimon.kmp.core.UseCase
import org.baimon.kmp.domain.task.TaskRepository
import org.baimon.kmp.domain.task.model.Task

class AddTaskUseCase(
    private val repository: TaskRepository
) : UseCase<Task, Unit> {

    override suspend fun execute(request: Task): Flow<Unit> {
        return repository.insertTask(request)
    }
}