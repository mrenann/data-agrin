package com.mrenann.dataagrin.tasks.data.source

import com.mrenann.dataagrin.core.data.local.dao.ActivityDao
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.data.local.mappers.toDomain
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.endOfDay
import com.mrenann.dataagrin.core.utils.startOfDay
import com.mrenann.dataagrin.tasks.domain.source.TasksDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasksDataSourceImpl(
    private val dao: ActivityDao
) : TasksDataSource {

    override fun getTasksByDate(date: Long): Flow<List<ActivityInfo>> {
        val start = startOfDay(date)
        val end = endOfDay(date)
        return dao.getActivitiesByDate(start, end)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun updateStatus(id: Int, status: ActivityStatus) {
        dao.updateStatus(id, status)
    }

    override fun getActivityById(id: Int): Flow<ActivityInfo?> {
        return dao.getActivityById(id).map { entity ->
            entity?.toDomain()
        }
    }

}