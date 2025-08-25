package com.mrenann.dataagrin.activityLog.presentation.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mrenann.dataagrin.activityLog.presentation.components.ActivityBottomSheet
import com.mrenann.dataagrin.activityLog.presentation.components.ActivityList
import com.mrenann.dataagrin.activityLog.presentation.screenModel.ActivityScreenModel
import com.mrenann.dataagrin.core.ui.presentation.components.LoadingView
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.BarChart

class ActivityTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = strings.activities.tabTitle
            val icon = rememberVectorPainter(EvaIcons.Fill.BarChart)

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
                    .padding(
                        bottom = padding.calculateBottomPadding(),
                        start = padding.calculateStartPadding(LayoutDirection.Ltr),
                        end = padding.calculateEndPadding(LayoutDirection.Ltr)
                    )
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
fun ErrorView(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}