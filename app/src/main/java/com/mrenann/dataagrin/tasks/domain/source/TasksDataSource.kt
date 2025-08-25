package com.mrenann.dataagrin.tasks.domain.source

import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import kotlinx.coroutines.flow.Flow

interface TasksDataSource {
    fun getTasksByDate(date: Long): Flow<List<ActivityInfo>>
    suspend fun updateStatus(id: Int, status: ActivityStatus)
    fun getActivityById(id: Int): Flow<ActivityInfo?>
}
