package com.mrenann.dataagrin.activityLog.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Close

@Composable
fun ActivityItem(activity: ActivityInfo, onDelete: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = activity.type, style = MaterialTheme.typography.titleMedium)
                    Text(text = activity.field, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "${activity.startTime} - ${activity.endTime}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                IconButton(onClick = { onDelete(activity.id) }) {
                    Icon(EvaIcons.Outline.Close, contentDescription = "Delete")
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = activity.notes.ifBlank { strings.activities.withoutNotes },
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Status: ${activity.status.name}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
