package com.mrenann.dataagrin.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Droplet
import compose.icons.evaicons.fill.Sun
import compose.icons.evaicons.outline.Thermometer

@Composable
fun CurrentConditionsSection(weather: WeatherInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CurrentConditionItem(
            icon = rememberVectorPainter(EvaIcons.Outline.Thermometer),
            label = "Temperature",
            value = "${weather.current.temperature}°C"
        )
        CurrentConditionItem(
            icon = rememberVectorPainter(EvaIcons.Fill.Droplet),
            label = "Humidity",
            value = "${weather.current.humidity}%"
        )
        CurrentConditionItem(
            icon = rememberVectorPainter(EvaIcons.Fill.Sun),
            label = "Weather",
            value = "Sunny"
        )
    }
}