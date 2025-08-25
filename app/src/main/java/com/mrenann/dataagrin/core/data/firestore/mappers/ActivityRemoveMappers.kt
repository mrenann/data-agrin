package com.mrenann.dataagrin.core.data.firestore.mappers

import com.mrenann.dataagrin.core.data.firestore.model.ActivityRemote
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import java.time.LocalTime

fun ActivityInfo.toRemote(): ActivityRemote {
    return ActivityRemote(
        id = remoteId ?: "",
        localId = id,
        type = this.type,
        field = this.field,
        startTime = this.startTime.toString(),
        endTime = this.endTime.toString(),
        notes = this.notes,
        activityDate = this.activityDate,
        status = this.status.name
    )
}

fun ActivityRemote.toDomain(remoteId: String): ActivityInfo {
    return ActivityInfo(
        id = localId,
        remoteId = remoteId,
        type = this.type,
        field = this.field,
        startTime = LocalTime.parse(this.startTime),
        endTime = LocalTime.parse(this.endTime),
        notes = this.notes,
        activityDate = this.activityDate,
        status = ActivityStatus.valueOf(this.status)
    )
}