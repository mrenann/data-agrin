package com.mrenann.dataagrin.tasks.data.source

import com.mrenann.dataagrin.core.data.firestore.service.FirebaseService
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.tasks.domain.source.TasksRemoteDataSource
import kotlinx.coroutines.tasks.await

class TasksRemoteDataSourceImpl(
    private val service: FirebaseService
) : TasksRemoteDataSource {

    override suspend fun updateStatus(remoteId: String, status: ActivityStatus) {
        service.activitiesCollection().document(remoteId)
            .update("status", status.name)
            .await()
    }
}