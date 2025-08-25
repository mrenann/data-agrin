package com.mrenann.dataagrin.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mrenann.dataagrin.core.domain.model.HourlyForecast
import com.mrenann.dataagrin.core.utils.getIconFromCode
import com.mrenann.dataagrin.core.utils.isoToBrtTime

@Composable
fun HourlyForecastItem(hourly: HourlyForecast) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = isoToBrtTime(hourly.time),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
        Icon(
            imageVector = getIconFromCode(code = hourly.code, isDayTime = hourly.isDay),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = "${hourly.temperature}°",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}