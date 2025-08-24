package com.mrenann.dataagrin.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.lyricist.strings
import compose.icons.WeatherIcons
import compose.icons.weathericons.DayCloudy
import compose.icons.weathericons.DayCloudyHigh
import compose.icons.weathericons.DayRain
import compose.icons.weathericons.DayShowers
import compose.icons.weathericons.DaySleetStorm
import compose.icons.weathericons.DaySprinkle
import compose.icons.weathericons.DaySunny
import compose.icons.weathericons.DayThunderstorm
import compose.icons.weathericons.NightClear
import compose.icons.weathericons.NightCloudy
import compose.icons.weathericons.NightCloudyHigh
import compose.icons.weathericons.NightRain
import compose.icons.weathericons.NightShowers
import compose.icons.weathericons.NightSleetStorm
import compose.icons.weathericons.NightSprinkle
import compose.icons.weathericons.NightThunderstorm

@Composable
fun getWeatherDescription(weatherCode: Int): String {
    return when (weatherCode) {
        0 -> strings.core.weather.clearSky
        1 -> strings.core.weather.fewClouds
        2 -> strings.core.weather.scatteredClouds
        3 -> strings.core.weather.brokenClouds
        4 -> strings.core.weather.overcastClouds
        5 -> strings.core.weather.lightRain
        6 -> strings.core.weather.moderateRain
        7 -> strings.core.weather.heavyRain
        8 -> strings.core.weather.veryHeavyRain
        9 -> strings.core.weather.extremeRain
        else -> strings.core.weather.unknown
    }
}

fun getIconFromCode(code: Int, isDayTime: Boolean): ImageVector {
    return if (isDayTime) {
        getWeatherIconDay(code)
    } else {
        getWeatherIconNight(code)
    }
}

fun getWeatherIconDay(weatherCode: Int): ImageVector {
    return when (weatherCode) {
        0 -> WeatherIcons.DaySunny
        1 -> WeatherIcons.DayCloudy
        2 -> WeatherIcons.DayCloudy
        3 -> WeatherIcons.DayCloudyHigh
        4 -> WeatherIcons.DayCloudyHigh
        5 -> WeatherIcons.DaySprinkle
        6 -> WeatherIcons.DayRain
        7 -> WeatherIcons.DayShowers
        8 -> WeatherIcons.DayThunderstorm
        9 -> WeatherIcons.DaySleetStorm
        else -> WeatherIcons.DaySunny
    }
}

fun getWeatherIconNight(weatherCode: Int): ImageVector {
    return when (weatherCode) {
        0 -> WeatherIcons.NightClear
        1 -> WeatherIcons.NightCloudy
        2 -> WeatherIcons.NightCloudy
        3 -> WeatherIcons.NightCloudyHigh
        4 -> WeatherIcons.NightCloudyHigh
        5 -> WeatherIcons.NightSprinkle
        6 -> WeatherIcons.NightRain
        7 -> WeatherIcons.NightShowers
        8 -> WeatherIcons.NightThunderstorm
        9 -> WeatherIcons.NightSleetStorm
        else -> WeatherIcons.NightClear
    }
}
