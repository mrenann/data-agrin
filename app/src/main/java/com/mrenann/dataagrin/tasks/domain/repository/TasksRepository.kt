package com.mrenann.dataagrin.tasks.domain.repository

import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasksByDate(date: Long): Flow<Resource<List<ActivityInfo>>>
    suspend fun updateStatus(id: Int, status: ActivityStatus): Flow<Resource<Unit>>
}
