package com.mrenann.dataagrin.core.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mrenann.dataagrin.core.utils.LocalTimeConverter
import java.time.LocalTime

@Entity(tableName = "activities")
@TypeConverters(LocalTimeConverter::class)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteId: String = "",
    val type: String,
    val field: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val activityDate: Long,
    val status: ActivityStatus = ActivityStatus.PENDING
)

enum class ActivityStatus {
    PENDING,
    IN_PROGRESS,
    DONE
}
