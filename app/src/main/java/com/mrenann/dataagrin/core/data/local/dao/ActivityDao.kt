package com.mrenann.dataagrin.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrenann.dataagrin.core.data.local.entity.ActivityEntity
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ActivityEntity)

    @Query("SELECT * FROM activities ORDER BY createdAt DESC")
    fun getAllActivities(): Flow<List<ActivityEntity>>

    @Query("DELETE FROM activities WHERE id = :id")
    suspend fun deleteActivity(id: Int)

    @Query("SELECT * FROM activities WHERE activityDate BETWEEN :start AND :end ORDER BY startTime ASC")
    fun getActivitiesByDate(start: Long, end: Long): Flow<List<ActivityEntity>>


    @Query("UPDATE activities SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: ActivityStatus)
}
