package org.baimon.kmp.domain.task.usecase

import kotlinx.coroutines.flow.Flow
import org.baimon.kmp.core.UseCase
import org.baimon.kmp.domain.task.TaskRepository
import org.baimon.kmp.domain.task.model.Task

class GetAllTaskUseCase(
    private val repository: TaskRepository
) : UseCase<Unit, List<Task>> {

    override suspend fun execute(request: Unit): Flow<List<Task>> {
        return repository.getAllTask()
    }
}