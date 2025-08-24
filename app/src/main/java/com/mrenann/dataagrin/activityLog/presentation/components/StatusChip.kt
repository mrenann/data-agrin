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

@Composable
fun StatusChip(status: ActivityStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        ActivityStatus.DONE -> Triple(
            Color(0xFFE0F8E1),
            Color(0xFF2E7D32),
            strings.activities.done
        )

        ActivityStatus.IN_PROGRESS -> Triple(
            Color(0xFFFFF9C4),
            Color(0xFFF9A825),
            strings.activities.inProgress
        )

        ActivityStatus.PENDING -> Triple(
            Color(0xFFFFF0E1),
            Color(0xFFE65100),
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