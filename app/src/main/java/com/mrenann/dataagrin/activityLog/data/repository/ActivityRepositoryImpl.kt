package com.mrenann.dataagrin.activityLog.data.repository

import com.mrenann.dataagrin.activityLog.domain.repository.ActivityRepository
import com.mrenann.dataagrin.activityLog.domain.source.ActivityDataSource
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ActivityRepositoryImpl(
    private val dataSource: ActivityDataSource
) : ActivityRepository {

    override fun getAllActivities(): Flow<Resource<List<ActivityInfo>>> = flow {
        emit(Resource.Loading)
        try {
            dataSource.getAllActivities().collect { list ->
                emit(Resource.Success(list))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Erro ao carregar atividades", e))
        }
    }

    override suspend fun insertActivity(activity: ActivityInfo): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            dataSource.insertActivity(activity)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error("Erro ao salvar atividade", e))
        }
    }

    override suspend fun deleteActivity(id: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            dataSource.deleteActivity(id)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error("Erro ao deletar atividade", e))
        }
    }
}

