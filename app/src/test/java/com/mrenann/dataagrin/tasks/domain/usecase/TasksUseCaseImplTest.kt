package com.mrenann.dataagrin.tasks.domain.usecase

import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.tasks.domain.repository.TasksRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
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
class TasksUseCaseImplTest {

    private lateinit var repository: TasksRepository
    private lateinit var useCase: TasksUseCaseImpl

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = TasksUseCaseImpl(repository)
    }

    @Test
    fun `getTasksByDate emits Loading then Success`() = runTest {
        val date = 123L
        val list = listOf(mockk<ActivityInfo>(), mockk())
        every { repository.getTasksByDate(date) } returns flowOf(Resource.Success(list))

        val emissions = useCase.getTasksByDate(date).toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)
        val success = emissions[1] as Resource.Success<List<ActivityInfo>>
        assertEquals(list, success.data)

        verify(exactly = 1) { repository.getTasksByDate(date) }
        confirmVerified(repository)
    }

    @Test
    fun `getTasksByDate emits Loading then Error when upstream throws`() = runTest {
        val date = 999L
        every { repository.getTasksByDate(date) } returns flow { throw RuntimeException("boom") }

        val emissions = useCase.getTasksByDate(date).toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Error)

        verify(exactly = 1) { repository.getTasksByDate(date) }
        confirmVerified(repository)
    }

    @Test
    fun `updateStatus emits Loading then Success`() = runTest {
        val id = 42
        val status = mockk<ActivityStatus>(relaxed = true)
        coEvery { repository.updateStatus(id, status) } returns flowOf(Resource.Success(Unit))

        val emissions = useCase.updateStatus(id, status).toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)

        coVerify(exactly = 1) { repository.updateStatus(id, status) }
        confirmVerified(repository)
    }

    @Test
    fun `updateStatus emits Loading then Error when upstream throws`() = runTest {
        val id = 7
        val status = mockk<ActivityStatus>(relaxed = true)
        coEvery {
            repository.updateStatus(
                id,
                status
            )
        } returns flow { throw RuntimeException("fail") }

        val emissions = useCase.updateStatus(id, status).toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Error)

        coVerify(exactly = 1) { repository.updateStatus(id, status) }
        confirmVerified(repository)
    }
}