package com.mrenann.dataagrin.tasks.data.repository

import app.cash.turbine.test
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.tasks.domain.source.TasksDataSource
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
class TasksRepositoryImplTest {

    private lateinit var dataSource: TasksDataSource
    private lateinit var repository: TasksRepositoryImpl

    @Before
    fun setUp() {
        dataSource = mockk(relaxed = true)
        repository = TasksRepositoryImpl(dataSource)
    }

    @Test
    fun `getTasksByDate emits Loading then Success for each upstream emission`() = runTest {
        val date = 123456789L
        val list1 = listOf(mockk<ActivityInfo>(), mockk())
        val list2 = listOf(mockk<ActivityInfo>())
        every { dataSource.getTasksByDate(date) } returns flowOf(list1, list2)

        repository.getTasksByDate(date).test {
            assertTrue(awaitItem() is Resource.Loading)

            val success1 = awaitItem() as Resource.Success<List<ActivityInfo>>
            assertEquals(list1, success1.data)

            val success2 = awaitItem() as Resource.Success<List<ActivityInfo>>
            assertEquals(list2, success2.data)

            awaitComplete()
        }

        verify(exactly = 1) { dataSource.getTasksByDate(date) }
        confirmVerified(dataSource)
    }

    @Test
    fun `getTasksByDate emits Loading then Error when upstream throws immediately`() = runTest {
        val date = 987654321L
        every { dataSource.getTasksByDate(date) } returns flow { throw RuntimeException("boom") }

        repository.getTasksByDate(date).test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        verify(exactly = 1) { dataSource.getTasksByDate(date) }
        confirmVerified(dataSource)
    }

    @Test
    fun `getTasksByDate emits Error after Success when upstream throws after an emission`() =
        runTest {
            val date = 13579L
            val list1 = listOf(mockk<ActivityInfo>())
            every { dataSource.getTasksByDate(date) } returns flow {
                emit(list1)
                throw RuntimeException("boom")
            }

            repository.getTasksByDate(date).test {
                assertTrue(awaitItem() is Resource.Loading)
                assertTrue(awaitItem() is Resource.Success)
                assertTrue(awaitItem() is Resource.Error)
                awaitComplete()
            }

            verify(exactly = 1) { dataSource.getTasksByDate(date) }
            confirmVerified(dataSource)
        }

    @Test
    fun `updateStatus emits Loading then Success and delegates to data source`() = runTest {
        val id = 42
        val status = mockk<ActivityStatus>(relaxed = true)
        coEvery { dataSource.updateStatus(id, status) } just Runs

        repository.updateStatus(id, status).test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Success)
            awaitComplete()
        }

        coVerify(exactly = 1) { dataSource.updateStatus(id, status) }
        confirmVerified(dataSource)
    }

    @Test
    fun `updateStatus emits Loading then Error when data source throws`() = runTest {
        val id = 7
        val status = mockk<ActivityStatus>(relaxed = true)
        coEvery { dataSource.updateStatus(id, status) } throws RuntimeException("fail")

        repository.updateStatus(id, status).test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        coVerify(exactly = 1) { dataSource.updateStatus(id, status) }
        confirmVerified(dataSource)
    }
}