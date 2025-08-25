package com.mrenann.dataagrin.tasks.data.repository

import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.tasks.domain.repository.TasksRepository
import com.mrenann.dataagrin.tasks.domain.source.TasksDataSource
import com.mrenann.dataagrin.tasks.domain.source.TasksRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class TasksRepositoryImpl(
    private val local: TasksDataSource,
    private val remote: TasksRemoteDataSource,
) : TasksRepository {

    override fun getTasksByDate(date: Long): Flow<Resource<List<ActivityInfo>>> = flow {
        emit(Resource.Loading)
        try {
            local.getTasksByDate(date).collect { list ->
                emit(Resource.Success(list))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Erro ao carregar tarefas", e))
        }
    }

    override suspend fun updateStatus(id: Int, status: ActivityStatus): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading)
            try {
                local.updateStatus(id, status)

                val activity = local.getActivityById(id).firstOrNull()

                activity?.remoteId?.let { remoteId ->
                    try {
                        remote.updateStatus(remoteId, status)
                    } catch (e: Exception) {
                        println("Falha ao sincronizar atualização de status: ${e.message}")
                    }
                }

                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error("Erro ao atualizar status da tarefa", e))
            }
        }
}


