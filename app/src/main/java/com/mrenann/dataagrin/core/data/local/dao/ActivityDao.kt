package com.mrenann.dataagrin.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrenann.dataagrin.core.data.local.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ActivityEntity)

    @Query("SELECT * FROM activities ORDER BY createdAt DESC")
    fun getAllActivities(): Flow<List<ActivityEntity>>

    @Query("DELETE FROM activities WHERE id = :id")
    suspend fun deleteActivity(id: Int)
}