package com.mrenann.dataagrin.activityLog.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.ui.presentation.components.PrimaryButton
import com.mrenann.dataagrin.core.ui.presentation.components.StyledTextField
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityBottomSheet(
    onDismiss: () -> Unit,
    onSave: (ActivityInfo) -> Unit
) {
    var type by remember { mutableStateOf("") }
    var plot by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf(LocalTime.now().withSecond(0).withNano(0)) }
    var endTime by remember { mutableStateOf(LocalTime.now().withSecond(0).withNano(0)) }


    val isFormValid = type.isNotBlank() &&
            plot.isNotBlank() &&
            (startTime.isBefore(endTime) || startTime.equals(endTime))

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = strings.activities.newActivity,
                style = MaterialTheme.typography.titleLarge
            )

            StyledTextField(
                value = type,
                onValueChange = { type = it },
                label = strings.activities.type
            )

            StyledTextField(
                value = plot,
                onValueChange = { plot = it },
                label = strings.activities.field
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    TimePickerField(
                        label = strings.activities.startTime,
                        time = startTime,
                        onTimeSelected = { startTime = it },
                        validate = { it.isBefore(endTime) }
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    TimePickerField(
                        label = strings.activities.endTime,
                        time = endTime,
                        onTimeSelected = { endTime = it },
                        validate = { it.isAfter(startTime) }
                    )
                }
            }


            NotesField(notes) { notes = it }

            PrimaryButton(
                text = strings.activities.saveBtn,
                onClick = {
                    val activity = ActivityInfo(
                        type = type,
                        field = plot,
                        startTime = startTime,
                        endTime = endTime,
                        notes = notes,
                        activityDate = System.currentTimeMillis(),
                        status = ActivityStatus.PENDING
                    )
                    onSave(activity)
                },
                enabled = isFormValid
            )

        }
    }
}
