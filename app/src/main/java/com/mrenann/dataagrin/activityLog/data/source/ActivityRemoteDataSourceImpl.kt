package com.mrenann.dataagrin.activityLog.data.source

import com.mrenann.dataagrin.activityLog.domain.source.ActivityRemoteDataSource
import com.mrenann.dataagrin.core.data.firestore.mappers.toDomain
import com.mrenann.dataagrin.core.data.firestore.mappers.toRemote
import com.mrenann.dataagrin.core.data.firestore.model.ActivityRemote
import com.mrenann.dataagrin.core.data.firestore.service.FirebaseService
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ActivityRemoteDataSourceImpl(
    private val service: FirebaseService
) : ActivityRemoteDataSource {

    override suspend fun insertActivity(activity: ActivityInfo): String {
        val docRef = service.activitiesCollection().document()
        val remote = activity.toRemote()
        docRef.set(remote).await()
        return docRef.id
    }

    override fun getAllActivities(): Flow<List<ActivityInfo>> = callbackFlow {
        val listener = service.activitiesCollection()
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val list = snapshot?.documents?.mapNotNull { doc ->
                    val activityRemote = doc.toObject(ActivityRemote::class.java)
                    activityRemote?.toDomain(doc.id)
                } ?: emptyList()

                trySend(list)
            }

        awaitClose { listener.remove() }
    }

    override suspend fun deleteActivity(id: String) {
        service.activitiesCollection().document(id).delete().await()
    }
}
