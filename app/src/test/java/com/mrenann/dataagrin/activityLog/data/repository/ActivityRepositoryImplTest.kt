package com.mrenann.dataagrin.activityLog.data.repository

import com.mrenann.dataagrin.activityLog.domain.source.ActivityDataSource
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
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
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActivityRepositoryImplTest {

    private lateinit var dataSource: ActivityDataSource
    private lateinit var repository: ActivityRepositoryImpl

    @Before
    fun setUp() {
        dataSource = mockk(relaxed = true)
        repository = ActivityRepositoryImpl(dataSource)
    }

    @Test
    fun `getAllActivities emits Loading then Success for each upstream emission`() = runTest {
        val list1 = listOf(mockk<ActivityInfo>(), mockk())
        val list2 = listOf(mockk<ActivityInfo>())
        every { dataSource.getAllActivities() } returns flowOf(list1, list2)

        val emissions = repository.getAllActivities().toList(mutableListOf())

        assertEquals(3, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)

        assertTrue(emissions[1] is Resource.Success)
        val success1 = emissions[1] as Resource.Success<List<ActivityInfo>>
        assertEquals(list1, success1.data)

        assertTrue(emissions[2] is Resource.Success)
        val success2 = emissions[2] as Resource.Success<List<ActivityInfo>>
        assertEquals(list2, success2.data)

        verify(exactly = 1) { dataSource.getAllActivities() }
        confirmVerified(dataSource)
    }

    @Test
    fun `getAllActivities emits Error when upstream throws after an emission`() = runTest {
        val list1 = listOf(mockk<ActivityInfo>())
        every { dataSource.getAllActivities() } returns flow {
            emit(list1)
            throw RuntimeException("boom")
        }

        val emissions = repository.getAllActivities().toList(mutableListOf())

        assertEquals(3, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)
        assertTrue(emissions[2] is Resource.Error)

        verify(exactly = 1) { dataSource.getAllActivities() }
        confirmVerified(dataSource)
    }

    @Test
    fun `insertActivity emits Loading then Success and delegates to data source`() = runTest {
        val activity = mockk<ActivityInfo>(relaxed = true)
        coEvery { dataSource.insertActivity(activity) } just Runs

        val emissions = repository.insertActivity(activity).toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)

        coVerify(exactly = 1) { dataSource.insertActivity(activity) }
        confirmVerified(dataSource)
    }

    @Test
    fun `insertActivity emits Error when data source throws`() = runTest {
        val activity = mockk<ActivityInfo>(relaxed = true)
        coEvery { dataSource.insertActivity(activity) } throws RuntimeException("save failed")

        val emissions = repository.insertActivity(activity).toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Error)

        coVerify(exactly = 1) { dataSource.insertActivity(activity) }
        confirmVerified(dataSource)
    }

    @Test
    fun `deleteActivity emits Loading then Success and delegates to data source`() = runTest {
        coEvery { dataSource.deleteActivity(42) } just Runs

        val emissions = repository.deleteActivity(42).toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)

        coVerify(exactly = 1) { dataSource.deleteActivity(42) }
        confirmVerified(dataSource)
    }

    @Test
    fun `deleteActivity emits Error when data source throws`() = runTest {
        coEvery { dataSource.deleteActivity(7) } throws RuntimeException("delete failed")

        val emissions = repository.deleteActivity(7).toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Error)

        coVerify(exactly = 1) { dataSource.deleteActivity(7) }
        confirmVerified(dataSource)
    }
}