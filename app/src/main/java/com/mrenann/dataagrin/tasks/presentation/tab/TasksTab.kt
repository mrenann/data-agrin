package com.mrenann.dataagrin.tasks.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.ui.presentation.components.ErrorView
import com.mrenann.dataagrin.core.ui.presentation.components.LoadingView
import com.mrenann.dataagrin.core.utils.formatDate
import com.mrenann.dataagrin.tasks.presentation.components.TasksScreen
import com.mrenann.dataagrin.tasks.presentation.screenModel.TasksScreenModel
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.List

class TasksTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = strings.tasks.tabTitle
            val icon = rememberVectorPainter(EvaIcons.Fill.List)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<TasksScreenModel>()
        val today = remember { System.currentTimeMillis() }

        LaunchedEffect(Unit) {
            screenModel.loadTasksByDate(today)
        }

        val state by screenModel.state.collectAsState()
        val onToggleStatusCallback = remember<(ActivityInfo) -> Unit> {
            { task ->
                val newStatus = when (task.status) {
                    ActivityStatus.PENDING -> ActivityStatus.IN_PROGRESS
                    ActivityStatus.IN_PROGRESS -> ActivityStatus.DONE
                    else -> ActivityStatus.PENDING
                }
                screenModel.updateTaskStatus(task.id, newStatus, task.activityDate)
            }
        }

        when (val currentState = state) {
            is TasksScreenModel.State.Loading -> LoadingView()
            is TasksScreenModel.State.Error -> ErrorView(
                message = currentState.message,
                onRetry = { screenModel.loadTasksByDate(today) }
            )

            is TasksScreenModel.State.Success -> {
                TasksScreen(
                    tasks = currentState.tasks,
                    onToggleStatus = onToggleStatusCallback,
                    currentDate = remember(today) { formatDate(today) }
                )
            }
        }
    }
}