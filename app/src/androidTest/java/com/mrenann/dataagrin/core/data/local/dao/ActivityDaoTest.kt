package com.mrenann.dataagrin.core.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.mrenann.dataagrin.core.data.local.databases.WeatherDatabase
import com.mrenann.dataagrin.core.data.local.entity.ActivityEntity
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
@SmallTest
class ActivityDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var activityDao: ActivityDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()
        activityDao = database.activityDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertActivity_and_getAllActivities() = runTest {
        val activity = ActivityEntity(
            id = 1,
            type = "Irrigação",
            field = "Campo A",
            startTime = LocalTime.now(),
            endTime = LocalTime.now().plusHours(1),
            activityDate = System.currentTimeMillis(),
            status = ActivityStatus.PENDING
        )
        activityDao.insertActivity(activity)

        val activities = activityDao.getAllActivities().first()

        assertTrue(activities.contains(activity))
        assertEquals(1, activities.size)
    }

    @Test
    fun deleteActivity_removesItFromDatabase() = runTest {
        val activity = ActivityEntity(
            id = 1,
            type = "Colheita",
            field = "Campo B",
            startTime = LocalTime.now(),
            endTime = LocalTime.now().plusHours(2),
            activityDate = System.currentTimeMillis(),
            status = ActivityStatus.PENDING
        )
        activityDao.insertActivity(activity)

        activityDao.deleteActivity(activity.id)

        val activities = activityDao.getAllActivities().first()
        assertFalse(activities.contains(activity))
        assertTrue(activities.isEmpty())
    }

    @Test
    fun getActivitiesByDate_returnsCorrectActivities() = runTest {
        val today = System.currentTimeMillis()
        val yesterday = today - 86400000

        val activityToday = ActivityEntity(
            id = 1,
            type = "Plantio",
            field = "Campo C",
            startTime = LocalTime.now(),
            endTime = LocalTime.now(),
            activityDate = today,
            status = ActivityStatus.DONE
        )
        val activityYesterday = ActivityEntity(
            id = 2,
            type = "Manutenção",
            field = "Campo D",
            startTime = LocalTime.now(),
            endTime = LocalTime.now(),
            activityDate = yesterday,
            status = ActivityStatus.IN_PROGRESS
        )

        activityDao.insertActivity(activityToday)
        activityDao.insertActivity(activityYesterday)

        val startOfDay = today - (today % 86400000)
        val endOfDay = startOfDay + 86400000 - 1

        val retrievedActivities = activityDao.getActivitiesByDate(startOfDay, endOfDay).first()

        assertTrue(retrievedActivities.contains(activityToday))
        assertFalse(retrievedActivities.contains(activityYesterday))
        assertEquals(1, retrievedActivities.size)
    }

    @Test
    fun updateStatus_changesActivityStatus() = runTest {
        val activity = ActivityEntity(
            id = 1,
            type = "Pulverização",
            field = "Campo E",
            startTime = LocalTime.now(),
            endTime = LocalTime.now(),
            activityDate = System.currentTimeMillis(),
            status = ActivityStatus.PENDING
        )
        activityDao.insertActivity(activity)

        activityDao.updateStatus(activity.id, ActivityStatus.DONE)

        val activities = activityDao.getAllActivities().first()
        val updatedActivity = activities.find { it.id == activity.id }

        assertNotNull(updatedActivity)
        assertEquals(ActivityStatus.DONE, updatedActivity?.status)
    }
}