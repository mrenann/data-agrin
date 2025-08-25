package com.mrenann.dataagrin.activityLog.domain.source

import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import kotlinx.coroutines.flow.Flow

interface ActivityDataSource {
    suspend fun insertActivity(activity: ActivityInfo)
    fun getAllActivities(): Flow<List<ActivityInfo>>
    suspend fun deleteActivity(id: Int)
    suspend fun insertAll(activities: List<ActivityInfo>)
    suspend fun updateAll(activities: List<ActivityInfo>)
    suspend fun deleteAll(activities: List<ActivityInfo>)
    suspend fun getLocalRemoteIds(): List<String?>
    fun getActivityById(id: Int): Flow<ActivityInfo?>
}