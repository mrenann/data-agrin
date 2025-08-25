package com.mrenann.dataagrin.activityLog.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.ui.theme.Done
import com.mrenann.dataagrin.core.ui.theme.InProgress
import com.mrenann.dataagrin.core.ui.theme.Pending

@Composable
fun StatusChip(status: ActivityStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        ActivityStatus.DONE -> Triple(
            Color.White,
            Done,
            strings.activities.done
        )

        ActivityStatus.IN_PROGRESS -> Triple(
            Color.White,
            InProgress,
            strings.activities.inProgress
        )

        ActivityStatus.PENDING -> Triple(
            Color.White,
            Pending,
            strings.activities.pending
        )
    }

    Surface(
        shape = MaterialTheme.shapes.small,
        color = backgroundColor,
        contentColor = textColor,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}