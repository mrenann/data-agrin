package com.mrenann.dataagrin.tasks.presentation.screenModel

import app.cash.turbine.test
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.tasks.domain.usecase.TasksUseCase
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
class TasksScreenModelTest {
    private val tasksUseCase = mockk<TasksUseCase>()
    private val dispatcher = StandardTestDispatcher()
    private lateinit var screenModel: TasksScreenModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
        screenModel = TasksScreenModel(tasksUseCase = tasksUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() {
        assertTrue(screenModel.state.value is TasksScreenModel.State.Loading)
    }

    @Test
    fun `loadTasksByDate should emit Loading then Success`() = runTest {
        val date = 12345L
        val mockTasks = listOf(
            ActivityInfo(
                id = 1,
                type = "teste",
                field = "teste A",
                startTime = LocalTime.now(),
                endTime = LocalTime.now(),
                activityDate = date,
                status = ActivityStatus.PENDING
            )
        )

        coEvery { tasksUseCase.getTasksByDate(date) } returns flowOf(
            Resource.Success(mockTasks)
        )

        screenModel.loadTasksByDate(date)

        screenModel.state.test(timeout = 3.seconds) {
            assertTrue(awaitItem() is TasksScreenModel.State.Loading)
            val successState = awaitItem()
            assertTrue(successState is TasksScreenModel.State.Success)
            assertEquals(mockTasks, (successState as TasksScreenModel.State.Success).tasks)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadTasksByDate should emit Loading then Error`() = runTest {
        val date = 12345L
        val errorMessage = "Erro"

        coEvery { tasksUseCase.getTasksByDate(date) } returns flowOf(
            Resource.Error(errorMessage)
        )

        screenModel.loadTasksByDate(date)

        screenModel.state.test(timeout = 3.seconds) {
            assertTrue(awaitItem() is TasksScreenModel.State.Loading)
            val errorState = awaitItem()
            assertTrue(errorState is TasksScreenModel.State.Error)
            assertEquals(errorMessage, (errorState as TasksScreenModel.State.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

}