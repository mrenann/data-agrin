package com.mrenann.dataagrin.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import compose.icons.WeatherIcons
import compose.icons.weathericons.Humidity
import compose.icons.weathericons.Rain

@Composable
fun ConditionsGrid(weather: WeatherInfo) {
    val conditions = listOf(
        Condition(WeatherIcons.Humidity, strings.weather.humidity, "${weather.current.humidity}%"),
        Condition(WeatherIcons.Rain, strings.weather.rain, "${weather.current.rain}mm"),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        userScrollEnabled = false
    ) {
        items(conditions) { condition ->
            ConditionGridItem(condition)
        }
    }
}

@Composable
private fun ConditionGridItem(condition: Condition) {
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = condition.icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = condition.label,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = condition.value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
    }
}

private data class Condition(val icon: ImageVector, val label: String, val value: String)