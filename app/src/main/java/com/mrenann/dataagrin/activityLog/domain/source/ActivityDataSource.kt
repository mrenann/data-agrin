package com.mrenann.dataagrin.activityLog.domain.source

import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import kotlinx.coroutines.flow.Flow

interface ActivityDataSource {
    suspend fun insertActivity(activity: ActivityInfo)
    fun getAllActivities(): Flow<List<ActivityInfo>>
    suspend fun deleteActivity(id: Int)
}