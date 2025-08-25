package com.mrenann.dataagrin.activityLog.data.source

import com.mrenann.dataagrin.core.data.local.dao.ActivityDao
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActivityDataSourceImplTest {

    private lateinit var dao: ActivityDao
    private lateinit var dataSource: ActivityDataSourceImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        dataSource = ActivityDataSourceImpl(dao)
    }

    @Test
    fun `getAllActivities emits empty list when DAO returns empty`() = runTest {
        every { dao.getAllActivities() } returns flowOf(emptyList())

        val result = dataSource.getAllActivities().first()

        assertTrue(result.isEmpty())
        io.mockk.verify(exactly = 1) { dao.getAllActivities() }
        confirmVerified(dao)
    }

    @Test
    fun `deleteActivity delegates to DAO with correct id`() = runTest {
        coEvery { dao.deleteActivity(any()) } just Runs
        val id = 123

        dataSource.deleteActivity(id)

        coVerify(exactly = 1) { dao.deleteActivity(id) }
        confirmVerified(dao)
    }

    @Test
    fun `insertActivity delegates to DAO`() = runTest {
        coEvery { dao.insertActivity(any()) } just Runs
        val activity = mockk<ActivityInfo>(relaxed = true)

        dataSource.insertActivity(activity)

        coVerify(exactly = 1) { dao.insertActivity(any()) }
        confirmVerified(dao)
    }
}