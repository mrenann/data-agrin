package com.mrenann.dataagrin.tasks.presentation.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.ui.presentation.components.EmptyView
import com.mrenann.dataagrin.core.ui.presentation.components.ErrorView
import com.mrenann.dataagrin.core.ui.presentation.components.LoadingView
import com.mrenann.dataagrin.core.ui.theme.Done
import com.mrenann.dataagrin.core.ui.theme.InProgress
import com.mrenann.dataagrin.core.ui.theme.Pending
import com.mrenann.dataagrin.core.utils.formatDate
import com.mrenann.dataagrin.tasks.presentation.screenModel.TasksScreenModel
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.CheckmarkCircle
import compose.icons.evaicons.fill.Clock
import compose.icons.evaicons.fill.List
import compose.icons.evaicons.fill.Sync

class TasksTab() : Tab {

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
        val today = System.currentTimeMillis()

        LaunchedEffect(Unit) {
            screenModel.loadTasksByDate(today)
        }
        TasksScreen(
            onRetry = { screenModel.loadTasksByDate(today) },
            onToggleStatus = { task ->
                val newStatus =
                    when (task.status) {
                        ActivityStatus.PENDING -> ActivityStatus.IN_PROGRESS
                        ActivityStatus.IN_PROGRESS -> ActivityStatus.DONE
                        else -> ActivityStatus.PENDING
                    }
                screenModel.updateTaskStatus(task.id, newStatus, task.activityDate)
            },
            currentDate = remember(today) { formatDate(today) }
        )
    }

    @Composable
    fun TasksScreen(
        onRetry: () -> Unit,
        onToggleStatus: (ActivityInfo) -> Unit,
        currentDate: String
    ) {
        val screenModel = koinScreenModel<TasksScreenModel>()
        val state by screenModel.state.collectAsState()

        when (val currentState = state) {
            is TasksScreenModel.State.Loading -> LoadingView()
            is TasksScreenModel.State.Error -> ErrorView(
                message = currentState.message,
                onRetry = onRetry
            )

            is TasksScreenModel.State.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(Modifier.height(16.dp))
                    TasksSummaryCard(tasks = currentState.tasks, currentDate = currentDate)
                    Spacer(Modifier.height(24.dp))
                    if (currentState.tasks.isEmpty()) {
                        EmptyView(strings.tasks.withoutTasksToday)
                    } else {
                        TasksContent(
                            tasks = currentState.tasks,
                            onToggleStatus = onToggleStatus,
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun TasksContent(
        tasks: List<ActivityInfo>,
        onToggleStatus: (ActivityInfo) -> Unit,
    ) {
        TasksList(tasks = tasks, onToggleStatus = onToggleStatus)
    }


    @Composable
    fun TasksSummaryCard(tasks: List<ActivityInfo>, currentDate: String) {
        val totalTasks = tasks.size
        val completedTasks = tasks.count { it.status == ActivityStatus.DONE }
        val progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks.toFloat() else 0f

        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            label = "progressAnimation"
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier.size(64.dp),
                        strokeWidth = 6.dp,
                        trackColor = MaterialTheme.colorScheme.surfaceContainer,
                        strokeCap = StrokeCap.Round
                    )
                    Text(
                        text = "${(animatedProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.width(20.dp))
                Column {
                    Text(
                        text = currentDate,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = strings.tasks.tasksFinished(completedTasks, totalTasks),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    @Composable
    fun TasksList(
        tasks: List<ActivityInfo>,
        onToggleStatus: (ActivityInfo) -> Unit
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskItem(task = task, onToggleStatus = { onToggleStatus(task) })
            }
        }
    }

    private fun getIconForTaskStatus(status: ActivityStatus): ImageVector {
        return when (status) {
            ActivityStatus.PENDING -> EvaIcons.Fill.Clock
            ActivityStatus.IN_PROGRESS -> EvaIcons.Fill.Sync
            ActivityStatus.DONE -> EvaIcons.Fill.CheckmarkCircle
        }
    }

    @Composable
    fun TaskItem(
        task: ActivityInfo,
        onToggleStatus: () -> Unit
    ) {
        val isDone = task.status == ActivityStatus.DONE
        val animatedColor by animateColorAsState(
            targetValue = when (task.status) {
                ActivityStatus.PENDING -> Pending
                ActivityStatus.IN_PROGRESS -> InProgress
                ActivityStatus.DONE -> Done
            },
            label = "statusColorAnimation"
        )

        val textColor =
            if (isDone) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface
        val textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { onToggleStatus() },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = getIconForTaskStatus(task.status),
                    contentDescription = "Status da tarefa",
                    tint = animatedColor,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = task.type,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor,
                        textDecoration = textDecoration
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = task.field,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor.copy(alpha = if (isDone) 0.6f else 0.8f),
                        textDecoration = textDecoration
                    )
                }
            }
        }
    }
}
