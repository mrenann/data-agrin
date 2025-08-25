package com.mrenann.dataagrin.tasks.data.source

import app.cash.turbine.test
import com.mrenann.dataagrin.core.data.local.dao.ActivityDao
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TasksDataSourceImplTest {

    private lateinit var dao: ActivityDao
    private lateinit var dataSource: TasksDataSourceImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        dataSource = TasksDataSourceImpl(dao)
    }

    @Test
    fun `getTasksByDate collects from dao and maps emissions`() = runTest {
        every { dao.getActivitiesByDate(any(), any()) } returns flowOf(emptyList(), emptyList())

        dataSource.getTasksByDate(123L).test {
            val l1 = awaitItem()
            assertTrue(l1.isEmpty())

            val l2 = awaitItem()
            assertTrue(l2.isEmpty())

            awaitComplete()
        }

        verify(exactly = 1) { dao.getActivitiesByDate(any(), any()) }
        confirmVerified(dao)
    }

    @Test
    fun `getTasksByDate propagates error when dao fails`() = runTest {
        every { dao.getActivitiesByDate(any(), any()) } returns flow {
            throw RuntimeException("boom")
        }

        dataSource.getTasksByDate(999L).test {
            val error = awaitError()
            assertEquals("boom", error.message)
        }

        verify(exactly = 1) { dao.getActivitiesByDate(any(), any()) }
        confirmVerified(dao)
    }

    @Test
    fun `updateStatus delegates to dao`() = runTest {
        val id = 42
        val status = mockk<ActivityStatus>(relaxed = true)
        coEvery { dao.updateStatus(id, status) } just Runs

        dataSource.updateStatus(id, status)

        coVerify(exactly = 1) { dao.updateStatus(id, status) }
        confirmVerified(dao)
    }
}