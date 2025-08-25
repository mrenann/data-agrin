package com.mrenann.dataagrin.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    @Query("SELECT * FROM activities WHERE id = :id")
    fun getActivityById(id: Int): Flow<ActivityEntity?>

    @Query("UPDATE activities SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: ActivityStatus)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(activities: List<ActivityEntity>)

    @Update
    suspend fun updateAll(activities: List<ActivityEntity>)

    @Delete
    suspend fun deleteAll(activities: List<ActivityEntity>)

    @Query("SELECT remoteId FROM activities")
    suspend fun getLocalRemoteIds(): List<String?>
}
