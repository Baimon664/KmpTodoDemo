package org.baimon.kmp.domain.task.usecase

import kotlinx.coroutines.flow.Flow
import org.baimon.kmp.core.UseCase
import org.baimon.kmp.domain.task.TaskRepository

class UpdateCheckTaskUseCase(
    private val repository: TaskRepository
): UseCase<Pair<Int, Boolean>, Unit> {

    override suspend fun execute(request: Pair<Int, Boolean>): Flow<Unit> {
        return repository.updateCheckById(request.first, request.second)
    }

}