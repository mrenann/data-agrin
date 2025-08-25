package com.mrenann.dataagrin.tasks.domain.source

import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus

interface TasksRemoteDataSource {
    suspend fun updateStatus(remoteId: String, status: ActivityStatus)
}