package com.mrenann.dataagrin.activityLog.domain.usecase

import com.mrenann.dataagrin.activityLog.domain.repository.ActivityRepository
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart


class ActivityUseCaseImpl(
    private val repository: ActivityRepository
) : ActivityUseCase {

    override fun getAllActivities(): Flow<Resource<List<ActivityInfo>>> =
        repository.getAllActivities()
            .onStart { emit(Resource.Loading) }
            .catch { emit(Resource.Error("Erro ao carregar atividades", it)) }

    override suspend fun insertActivity(activity: ActivityInfo): Flow<Resource<Unit>> =
        repository.insertActivity(activity)
            .onStart { emit(Resource.Loading) }
            .catch { emit(Resource.Error("Erro ao salvar atividade", it)) }

    override suspend fun deleteActivity(id: Int): Flow<Resource<Unit>> =
        repository.deleteActivity(id)
            .onStart { emit(Resource.Loading) }
            .catch { emit(Resource.Error("Erro ao deletar atividade", it)) }
}
