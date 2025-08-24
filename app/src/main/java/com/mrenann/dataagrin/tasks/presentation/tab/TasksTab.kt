package com.mrenann.dataagrin.tasks.presentation.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.tasks.presentation.screenModel.TasksScreenModel
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Home

class TasksTab() : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = strings.tasks.tabTitle
            val icon = rememberVectorPainter(EvaIcons.Fill.Home)

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
                    if (task.status == ActivityStatus.PENDING) ActivityStatus.DONE
                    else ActivityStatus.PENDING
                screenModel.updateTaskStatus(task.id, newStatus, task.activityDate)
            }
        )
    }

    @Composable
    fun TasksScreen(
        onRetry: () -> Unit,
        onToggleStatus: (ActivityInfo) -> Unit
    ) {
        val screenModel = koinScreenModel<TasksScreenModel>()
        val state by screenModel.state.collectAsState()

        when (state) {
            is TasksScreenModel.State.Loading -> LoadingView()
            is TasksScreenModel.State.Error -> ErrorView(
                message = (state as TasksScreenModel.State.Error).message,
                onRetry = onRetry
            )

            is TasksScreenModel.State.Success -> {
                if ((state as TasksScreenModel.State.Success).tasks.isEmpty()) {
                    EmptyView()
                } else {
                    TasksList(
                        tasks = (state as TasksScreenModel.State.Success).tasks,
                        onToggleStatus = onToggleStatus
                    )
                }
            }
        }
    }

    @Composable
    fun LoadingView() {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun ErrorView(message: String, onRetry: () -> Unit) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = message, color = Color.Red)
                Spacer(Modifier.height(8.dp))
                Button(onClick = onRetry) {
                    Text("Tentar novamente")
                }
            }
        }
    }

    @Composable
    fun EmptyView() {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nenhuma tarefa para hoje 🎉", style = MaterialTheme.typography.bodyLarge)
        }
    }


    @Composable
    fun TasksList(
        tasks: List<ActivityInfo>,
        onToggleStatus: (ActivityInfo) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tasks) { task ->
                TaskItem(task = task, onToggleStatus = { onToggleStatus(task) })
            }
        }
    }


    @Composable
    fun TaskItem(
        task: ActivityInfo,
        onToggleStatus: () -> Unit
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleStatus() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(task.type, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = task.field,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Checkbox(
                    checked = task.status == ActivityStatus.DONE,
                    onCheckedChange = { onToggleStatus() }
                )
            }
        }
    }

}


