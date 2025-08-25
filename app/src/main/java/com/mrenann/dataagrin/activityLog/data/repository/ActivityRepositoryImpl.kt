package com.mrenann.dataagrin.activityLog.data.repository

import com.mrenann.dataagrin.activityLog.domain.repository.ActivityRepository
import com.mrenann.dataagrin.activityLog.domain.source.ActivityDataSource
import com.mrenann.dataagrin.activityLog.domain.source.ActivityRemoteDataSource
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class ActivityRepositoryImpl(
    private val local: ActivityDataSource,
    private val remote: ActivityRemoteDataSource
) : ActivityRepository {

    override fun getAllActivities(): Flow<Resource<List<ActivityInfo>>> = flow {
        emit(Resource.Loading)
        try {
            val remoteList = remote.getAllActivities().firstOrNull() ?: emptyList()
            val localList = local.getAllActivities().firstOrNull() ?: emptyList()

            val remoteMap = remoteList.associateBy { it.remoteId }
            val localMap = localList.associateBy { it.remoteId }

            val itemsToInsert = remoteList.filter { remoteItem ->
                remoteItem.remoteId !in localMap
            }

            val itemsToUpdate = remoteList.filter { remoteItem ->
                val localItem = localMap[remoteItem.remoteId]
                localItem != null && remoteItem != localItem
            }

            val itemsToDelete = localList.filter { localItem ->
                localItem.remoteId !in remoteMap
            }

            if (itemsToInsert.isNotEmpty()) {
                local.insertAll(itemsToInsert)
            }
            if (itemsToUpdate.isNotEmpty()) {
                local.updateAll(itemsToUpdate)
            }
            if (itemsToDelete.isNotEmpty()) {
                local.deleteAll(itemsToDelete)
            }

            emit(Resource.Success(remoteList))

        } catch (e: Exception) {
            local.getAllActivities().collect { localList ->
                emit(Resource.Success(localList))
            }
        }
    }

    override suspend fun insertActivity(activity: ActivityInfo): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            val remoteId = remote.insertActivity(activity)

            val activityWithRemoteId = activity.copy(remoteId = remoteId)

            local.insertActivity(activityWithRemoteId)

            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error("Erro ao salvar atividade", e))
        }
    }

    override suspend fun deleteActivity(id: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            println("Iniciando a tentativa de exclusão para o id local: $id")

            val activity = local.getActivityById(id).firstOrNull()

            if (activity != null) {
                println("Atividade encontrada localmente: remoteId = ${activity.remoteId}, id = ${activity.id}")

                try {
                    activity.remoteId?.let { remoteId ->
                        println("Tentando deletar no Firebase com remoteId: $remoteId")
                        remote.deleteActivity(remoteId)
                        println("Exclusão no Firebase concluída com sucesso!")
                    } ?: println("remoteId é nulo, pulando exclusão no Firebase.")
                } catch (e: Exception) {
                    println("Falha ao deletar no Firebase: ${e.message}")
                }

                val rowsDeleted =
                    local.deleteActivity(id)

                println("Tentativa de exclusão local concluída. Linhas afetadas: $rowsDeleted")

                emit(Resource.Success(Unit))
            } else {
                println("Atividade com id local: $id NÃO foi encontrada no banco de dados.")
                emit(Resource.Error("Atividade não encontrada para ser deletada"))
            }
        } catch (e: Exception) {
            println("Erro fatal durante a exclusão da atividade: ${e.message}")
            emit(Resource.Error("Erro ao deletar atividade", e))
        }
    }
}

