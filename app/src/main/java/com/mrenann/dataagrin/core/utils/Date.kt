package com.mrenann.dataagrin.core.utils

import java.time.LocalDate
import java.time.ZoneId

fun startOfDay(timestamp: Long): Long {
    return LocalDate.ofEpochDay(timestamp / 86400000).atStartOfDay(ZoneId.systemDefault())
        .toInstant().toEpochMilli()
}

fun endOfDay(timestamp: Long): Long {
    return startOfDay(timestamp) + 24 * 60 * 60 * 1000 - 1
}
