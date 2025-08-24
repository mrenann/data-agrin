package com.mrenann.dataagrin.activityLog.presentation.components

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mrenann.dataagrin.core.ui.presentation.components.StyledTextField
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimePickerField(
    label: String,
    time: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    validate: ((LocalTime) -> Boolean)? = null
) {
    val context = LocalContext.current
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val displayText = time.format(timeFormatter)

    Box {
        StyledTextField(
            value = displayText,
            onValueChange = {},
            label = label,
            enabled = false,
            readOnly = true
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            val newTime = LocalTime.of(hour, minute)
                            val isValid = validate?.invoke(newTime) ?: true
                            if (isValid) {
                                onTimeSelected(newTime)
                            } else {
                                Toast
                                    .makeText(context, "Horário inválido", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        time.hour,
                        time.minute,
                        true
                    ).show()
                }
        )
    }
}
