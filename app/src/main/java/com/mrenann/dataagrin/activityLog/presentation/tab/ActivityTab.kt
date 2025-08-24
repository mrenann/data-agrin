package com.mrenann.dataagrin.activityLog.presentation.tab

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mrenann.dataagrin.activityLog.presentation.screenModel.ActivityScreenModel
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.FileRemove
import compose.icons.evaicons.fill.List
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ActivityTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = strings.activities.tabTitle
            val icon = rememberVectorPainter(EvaIcons.Fill.List)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<ActivityScreenModel>()
        val state by screenModel.state.collectAsState()

        var showBottomSheet by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            screenModel.loadActivities()
        }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { showBottomSheet = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Activity")
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                when (val s = state) {
                    is ActivityScreenModel.State.Loading -> LoadingView()
                    is ActivityScreenModel.State.Error -> ErrorView(s.message)
                    is ActivityScreenModel.State.Success -> ActivityList(
                        activities = s.activities,
                        onDelete = { id -> screenModel.deleteActivity(id) }
                    )
                }
            }

            if (showBottomSheet) {
                ActivityBottomSheet(
                    onDismiss = { showBottomSheet = false },
                    onSave = { activity ->
                        screenModel.insertActivity(activity)
                        showBottomSheet = false
                    }
                )
            }
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}

@Composable
fun ActivityList(
    activities: List<ActivityInfo>,
    onDelete: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(activities, key = { it.id }) { activity ->
            ActivityItem(activity, onDelete)
        }
    }
}

@Composable
fun ActivityItem(activity: ActivityInfo, onDelete: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
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
                Icon(EvaIcons.Fill.FileRemove, contentDescription = "Delete")
            }
        }
    }
}


@Composable
fun TimePickerField(label: String, time: LocalTime, onTimeSelected: (LocalTime) -> Unit) {
    val context = LocalContext.current
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    var displayText by remember { mutableStateOf(time.format(timeFormatter)) }

    Box(
        modifier = Modifier
            .height(20.dp)
            .fillMaxWidth()
            .clickable {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        val newTime = LocalTime.of(hour, minute)
                        displayText = newTime.format(timeFormatter)
                        onTimeSelected(newTime)
                    },
                    time.hour,
                    time.minute,
                    true
                ).show()
            }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityBottomSheet(
    onDismiss: () -> Unit,
    onSave: (ActivityInfo) -> Unit
) {
    var type by remember { mutableStateOf("") }
    var plot by remember { mutableStateOf("") }

    var notes by remember { mutableStateOf("") }

    var startTime by remember { mutableStateOf(LocalTime.now()) }
    var endTime by remember { mutableStateOf(LocalTime.now()) }


    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("New Activity", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Type") })
            OutlinedTextField(value = plot, onValueChange = { plot = it }, label = { Text("Plot") })
            TimePickerField("Start Time", startTime) { startTime = it }
            TimePickerField("End Time", endTime) { endTime = it }
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes") })

            Button(
                onClick = {
                    val activity = ActivityInfo(
                        id = 0,
                        type = type,
                        field = plot,
                        startTime = startTime,
                        endTime = endTime,
                        notes = notes
                    )
                    onSave(activity)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}