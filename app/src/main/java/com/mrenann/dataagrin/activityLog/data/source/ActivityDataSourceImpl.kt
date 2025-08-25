package com.mrenann.dataagrin.activityLog.data.source

import com.mrenann.dataagrin.activityLog.domain.source.ActivityDataSource
import com.mrenann.dataagrin.core.data.local.dao.ActivityDao
import com.mrenann.dataagrin.core.data.local.mappers.toDomain
import com.mrenann.dataagrin.core.data.local.mappers.toEntity
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActivityDataSourceImpl(
    private val dao: ActivityDao
) : ActivityDataSource {

    override suspend fun insertActivity(activity: ActivityInfo) {
        dao.insertActivity(activity.toEntity())
    }

    override fun getAllActivities(): Flow<List<ActivityInfo>> {
        return dao.getAllActivities().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun deleteActivity(id: Int) {
        dao.deleteActivity(id)
    }

    override fun getActivityById(id: Int): Flow<ActivityInfo?> {
        return dao.getActivityById(id).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun insertAll(activities: List<ActivityInfo>) {
        dao.insertAll(activities.map { it.toEntity() })
    }

    override suspend fun updateAll(activities: List<ActivityInfo>) {
        dao.updateAll(activities.map { it.toEntity() })
    }

    override suspend fun deleteAll(activities: List<ActivityInfo>) {
        dao.deleteAll(activities.map { it.toEntity() })
    }

    override suspend fun getLocalRemoteIds(): List<String?> {
        return dao.getLocalRemoteIds()
    }
}