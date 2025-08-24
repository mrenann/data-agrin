package com.mrenann.dataagrin.tasks.domain.usecase

import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.tasks.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart


class TasksUseCaseImpl(
    private val repository: TasksRepository
) : TasksUseCase {

    override fun getTasksByDate(date: Long): Flow<Resource<List<ActivityInfo>>> =
        repository.getTasksByDate(date)
            .onStart { emit(Resource.Loading) }
            .catch { emit(Resource.Error("Erro ao carregar tarefas", it)) }

    override suspend fun updateStatus(id: Int, status: ActivityStatus): Flow<Resource<Unit>> =
        repository.updateStatus(id, status)
            .onStart { emit(Resource.Loading) }
            .catch { emit(Resource.Error("Erro ao atualizar status da tarefa", it)) }
}