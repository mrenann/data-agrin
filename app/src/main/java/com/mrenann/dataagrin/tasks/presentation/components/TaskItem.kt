package com.mrenann.dataagrin.tasks.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.ui.theme.Done
import com.mrenann.dataagrin.core.ui.theme.InProgress
import com.mrenann.dataagrin.core.ui.theme.Pending

@Composable
fun TaskItem(
    task: ActivityInfo,
    onToggleStatus: (ActivityInfo) -> Unit
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
        if (isDone) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
    val textDecoration = if (isDone) TextDecoration.LineThrough else null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(animatedColor)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = task.type,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textDecoration = textDecoration
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = task.field,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.8f),
                    textDecoration = textDecoration
                )
            }

            StatusToggleButton(
                status = task.status,
                color = animatedColor,
                onClick = { onToggleStatus(task) },
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}