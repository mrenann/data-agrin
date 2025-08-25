package com.mrenann.dataagrin.tasks.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.ui.presentation.components.EmptyView

@Composable
fun TasksScreen(
    tasks: List<ActivityInfo>,
    onToggleStatus: (ActivityInfo) -> Unit,
    currentDate: String
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            TasksSummaryCard(tasks = tasks, currentDate = currentDate)
        }

        if (tasks.isEmpty()) {
            item {
                EmptyView(
                    title = strings.tasks.withoutTasksToday,
                    subTitle = strings.tasks.withoutTasksTodaySubtitle
                )
            }
        } else {
            items(tasks, key = { it.id }) { task ->
                TaskItem(task = task, onToggleStatus = onToggleStatus)
            }
        }
    }
}