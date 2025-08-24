package com.mrenann.dataagrin.core.data.local.mappers

import com.mrenann.dataagrin.core.data.local.entity.ActivityEntity
import com.mrenann.dataagrin.core.domain.model.ActivityInfo

fun ActivityEntity.toDomain(): ActivityInfo = ActivityInfo(
    id = id,
    type = type,
    field = field,
    startTime = startTime,
    endTime = endTime,
    notes = notes
)

fun ActivityInfo.toEntity(): ActivityEntity = ActivityEntity(
    id = id,
    type = type,
    field = field,
    startTime = startTime,
    endTime = endTime,
    notes = notes
)
