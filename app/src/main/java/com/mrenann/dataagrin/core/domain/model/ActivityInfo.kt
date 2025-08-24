package com.mrenann.dataagrin.core.domain.model

import java.time.LocalTime

data class ActivityInfo(
    val id: Int = 0,
    val type: String,
    val field: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val notes: String = ""
)