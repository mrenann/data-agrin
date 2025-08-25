package com.mrenann.dataagrin.activityLog.domain.usecase

import com.mrenann.dataagrin.activityLog.domain.repository.ActivityRepository
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActivityUseCaseImplTest {

    private lateinit var repository: ActivityRepository
    private lateinit var useCase: ActivityUseCaseImpl

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = ActivityUseCaseImpl(repository)
    }

    @Test
    fun `getAllActivities delegates to repository and returns same emissions`() = runTest {
        val list = listOf(mockk<ActivityInfo>(), mockk())
        every { repository.getAllActivities() } returns flowOf(
            Resource.Success(list)
        )

        val emissions = useCase.getAllActivities().toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)
        val success = emissions[1] as Resource.Success<List<ActivityInfo>>
        assertEquals(list, success.data)

        verify(exactly = 1) { repository.getAllActivities() }
        confirmVerified(repository)
    }

    @Test
    fun `insertActivity delegates to repository and returns its emissions`() = runTest {
        val activity = mockk<ActivityInfo>(relaxed = true)
        coEvery { repository.insertActivity(activity) } returns flowOf(
            Resource.Success(Unit)
        )

        val emissions = useCase.insertActivity(activity).toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)

        coVerify(exactly = 1) { repository.insertActivity(activity) }
        confirmVerified(repository)
    }

    @Test
    fun `deleteActivity delegates to repository and returns its emissions`() = runTest {
        val id = 42
        coEvery { repository.deleteActivity(id) } returns flowOf(
            Resource.Success(Unit)
        )

        val emissions = useCase.deleteActivity(id).toList(mutableListOf())

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)

        coVerify(exactly = 1) { repository.deleteActivity(id) }
        confirmVerified(repository)
    }
}