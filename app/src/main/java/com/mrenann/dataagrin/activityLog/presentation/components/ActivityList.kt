package com.mrenann.dataagrin.activityLog.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.ui.presentation.components.EmptyView
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ActivityList(
    activities: List<ActivityInfo>,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (activities.isEmpty()) {
        EmptyView(
            title = strings.activities.noActivitiesTitle,
            subTitle = strings.activities.noActivitiesSubtitle
        )
        return
    }

    val groupedActivities = remember(activities) {
        activities.groupBy {
            Instant.ofEpochMilli(it.activityDate).atZone(ZoneId.systemDefault()).toLocalDate()
        }.toSortedMap(compareByDescending { it })
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        item {
            Text(
                text = strings.activities.activityHistory,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        groupedActivities.forEach { (date, activitiesOnDate) ->
            item {
                DateHeader(date = date)
            }
            items(activitiesOnDate) { activity ->
                ActivityItem(
                    activity = activity,
                    onDelete = { onDelete(activity.id) }
                )
            }
        }
    }
}

@Composable
private fun DateHeader(date: LocalDate) {
    val headerText = when (date) {
        LocalDate.now() -> strings.activities.today
        LocalDate.now().minusDays(1) -> strings.activities.yesterday
        else -> date.format(
            DateTimeFormatter.ofPattern(
                "d 'de' MMMM 'de' yyyy",
                Locale("pt", "BR")
            )
        )
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    Text(
        text = headerText,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 8.dp)
    )
}


