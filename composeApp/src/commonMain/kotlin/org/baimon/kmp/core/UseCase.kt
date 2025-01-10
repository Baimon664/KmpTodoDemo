package org.baimon.kmp.core

import kotlinx.coroutines.flow.Flow

interface UseCase<T, R> {
    suspend fun execute(request: T): Flow<R>
}