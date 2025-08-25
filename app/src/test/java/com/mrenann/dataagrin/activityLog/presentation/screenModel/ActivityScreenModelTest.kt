package com.mrenann.dataagrin.activityLog.presentation.screenModel

import app.cash.turbine.test
import com.mrenann.dataagrin.activityLog.domain.usecase.ActivityUseCase
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalTime
import kotlin.time.Duration.Companion.seconds

@ExperimentalCoroutinesApi
class ActivityScreenModelTest {
    private val activityUseCase = mockk<ActivityUseCase>()
    private val dispatcher = StandardTestDispatcher()
    private lateinit var screenModel: ActivityScreenModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
        coEvery { activityUseCase.getAllActivities() } returns flowOf(Resource.Loading)
        screenModel = ActivityScreenModel(activityUseCase = activityUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() {
        assertTrue(screenModel.state.value is ActivityScreenModel.State.Loading)
    }

    @Test
    fun `loadActivities should emit Loading then Success`() = runTest {
        val mockActivities = listOf(
            ActivityInfo(
                id = 1, type = "zxzx", field = "C", startTime = LocalTime.now(),
                endTime = LocalTime.now(), activityDate = 1L, status = ActivityStatus.DONE
            )
        )
        coEvery { activityUseCase.getAllActivities() } returns flowOf(
            Resource.Success(mockActivities)
        )

        screenModel.loadActivities()

        screenModel.state.test(timeout = 3.seconds) {
            assertTrue(awaitItem() is ActivityScreenModel.State.Loading)
            val successState = awaitItem()
            assertTrue(successState is ActivityScreenModel.State.Success)
            assertEquals(
                mockActivities,
                (successState as ActivityScreenModel.State.Success).activities
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadActivities should emit Loading then Error`() = runTest {
        val errorMessage = "Erro"
        coEvery { activityUseCase.getAllActivities() } returns flowOf(
            Resource.Error(errorMessage)
        )

        screenModel.loadActivities()

        screenModel.state.test(timeout = 3.seconds) {
            assertTrue(awaitItem() is ActivityScreenModel.State.Loading)
            val errorState = awaitItem()
            assertTrue(errorState is ActivityScreenModel.State.Error)
            assertEquals(errorMessage, (errorState as ActivityScreenModel.State.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insertActivity should optimistically add to list`() = runTest {
        val initialActivities = listOf(
            ActivityInfo(
                id = 1, type = "zzd", field = "D", startTime = LocalTime.now(),
                endTime = LocalTime.now(), activityDate = 1L, status = ActivityStatus.DONE
            )
        )

        coEvery { activityUseCase.getAllActivities() } returns flowOf(
            Resource.Success(
                initialActivities
            )
        )
        screenModel.loadActivities()

        val newActivity = ActivityInfo(
            id = 2, type = "eeee", field = "E", startTime = LocalTime.now(),
            endTime = LocalTime.now(), activityDate = 2L, status = ActivityStatus.DONE
        )
        coEvery { activityUseCase.insertActivity(newActivity) } returns flowOf()

        screenModel.state.test(timeout = 3.seconds) {
            assertEquals(ActivityScreenModel.State.Loading, awaitItem())
            assertEquals(
                initialActivities,
                (awaitItem() as ActivityScreenModel.State.Success).activities
            )

            screenModel.insertActivity(newActivity)

            val updatedState = awaitItem() as ActivityScreenModel.State.Success
            assertEquals(2, updatedState.activities.size)
            assertEquals(newActivity, updatedState.activities.first())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `deleteActivity should optimistically remove from list`() = runTest {
        val activity1 = ActivityInfo(
            id = 1, type = "dddd", field = "D", startTime = LocalTime.now(),
            endTime = LocalTime.now(), activityDate = 1L, status = ActivityStatus.DONE
        )
        val activity2 = ActivityInfo(
            id = 2, type = "eee", field = "E", startTime = LocalTime.now(),
            endTime = LocalTime.now(), activityDate = 2L, status = ActivityStatus.DONE
        )
        val initialActivities = listOf(activity1, activity2)

        coEvery { activityUseCase.getAllActivities() } returns flowOf(
            Resource.Success(
                initialActivities
            )
        )
        screenModel.loadActivities()

        coEvery { activityUseCase.deleteActivity(1) } returns flowOf()

        screenModel.state.test(timeout = 3.seconds) {
            assertEquals(ActivityScreenModel.State.Loading, awaitItem())
            assertEquals(
                initialActivities,
                (awaitItem() as ActivityScreenModel.State.Success).activities
            )

            screenModel.deleteActivity(1)

            val updatedState = awaitItem() as ActivityScreenModel.State.Success
            assertEquals(1, updatedState.activities.size)
            assertTrue(updatedState.activities.none { it.id == 1 })
            ensureAllEventsConsumed()
        }
    }
}