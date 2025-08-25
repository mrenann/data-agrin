package com.mrenann.dataagrin.core.data.local.mappers

import com.mrenann.dataagrin.core.data.local.entity.ActivityEntity
import com.mrenann.dataagrin.core.domain.model.ActivityInfo

fun ActivityEntity.toDomain(): ActivityInfo = ActivityInfo(
    id = id,
    type = type,
    field = field,
    startTime = startTime,
    endTime = endTime,
    notes = notes,
    activityDate = activityDate,
    status = status
)

fun ActivityInfo.toEntity(): ActivityEntity {

    return ActivityEntity(
        id = id,
        status = status,
        type = type,
        field = field,
        startTime = startTime,
        endTime = endTime,
        notes = notes,
        activityDate = System.currentTimeMillis()
    )

}