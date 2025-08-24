package com.mrenann.dataagrin.activityLog.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import com.mrenann.dataagrin.core.ui.presentation.components.StyledTextField


@Composable
fun NotesField(
    notes: String,
    onNotesChange: (String) -> Unit
) {
    StyledTextField(
        value = notes,
        onValueChange = onNotesChange,
        label = strings.activities.notes,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        maxLines = 5
    )
}