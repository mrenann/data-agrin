package com.mrenann.dataagrin.core.data.firestore.model

import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus

data class ActivityRemote(
    val id: String = "",
    val localId: Int = 0,
    val type: String = "",
    val field: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val notes: String = "",
    val activityDate: Long = 0L,
    val status: String = ActivityStatus.PENDING.name
)