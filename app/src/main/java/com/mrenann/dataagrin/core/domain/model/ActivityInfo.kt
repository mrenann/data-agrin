package com.mrenann.dataagrin.core.domain.model

import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import java.time.LocalTime

data class ActivityInfo(
    val id: Int = 0,
    val type: String,
    val field: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val notes: String = "",
    val activityDate: Long,
    val status: ActivityStatus
)
