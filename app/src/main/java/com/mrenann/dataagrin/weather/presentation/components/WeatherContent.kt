package com.mrenann.dataagrin.weather.presentation.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import com.mrenann.dataagrin.core.domain.model.WeatherInfo


@Composable
fun WeatherContent(weather: WeatherInfo, fromCache: Boolean) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(vertical = 32.dp)
        ) {
            WeatherHeaderSection(weather = weather)

            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = strings.weather.hourlyForecast,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(16.dp))
                HourlyForecastSection(hourly = weather.hourly)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = strings.weather.weather,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(16.dp))
                ConditionsGrid(weather = weather)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = if (fromCache) strings.weather.dataFromLocal else strings.weather.dataFromAPI,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}