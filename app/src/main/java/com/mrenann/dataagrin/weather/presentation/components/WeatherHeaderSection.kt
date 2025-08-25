package com.mrenann.dataagrin.weather.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.core.utils.getIconFromCode
import com.mrenann.dataagrin.core.utils.getWeatherDescription

@Composable
fun WeatherHeaderSection(weather: WeatherInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = getIconFromCode(weather.current.code, weather.current.isDay),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${weather.current.temperature}°",
            fontSize = 86.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = getWeatherDescription(weather.current.code),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}