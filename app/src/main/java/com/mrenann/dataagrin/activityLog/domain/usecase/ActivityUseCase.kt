package com.mrenann.dataagrin.activityLog.domain.usecase


import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ActivityUseCase {
    fun getAllActivities(): Flow<Resource<List<ActivityInfo>>>
    suspend fun insertActivity(activity: ActivityInfo): Flow<Resource<Unit>>
    suspend fun deleteActivity(id: Int): Flow<Resource<Unit>>
}
