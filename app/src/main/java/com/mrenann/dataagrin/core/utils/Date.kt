package com.mrenann.dataagrin.core.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val DATE_FORMAT_PATTERN = "EEEE, dd/MM"
private val ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME
private val BRT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")
private val DATE_FORMATTER: DateTimeFormatter by lazy {
    DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN, Locale.getDefault())
}

fun startOfDay(timestamp: Long): Long {
    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun endOfDay(timestamp: Long): Long {
    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .atTime(LocalTime.MAX)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun formatDate(timestamp: Long): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val zonedDateTime = instant.atZone(ZoneId.systemDefault())
    return DATE_FORMATTER.format(zonedDateTime).replaceFirstChar { it.uppercaseChar() }
}

fun isoToBrtTime(isoString: String): String {
    val localDateTime = LocalDateTime.parse(isoString, ISO_FORMATTER)
    return localDateTime.format(BRT_FORMATTER)
}

