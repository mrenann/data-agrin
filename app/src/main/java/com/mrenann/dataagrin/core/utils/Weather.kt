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
        WeatherCodes.CLEAR_SKY -> strings.core.weather.clearSky
        WeatherCodes.FEW_CLOUDS -> strings.core.weather.fewClouds
        WeatherCodes.SCATTERED_CLOUDS -> strings.core.weather.scatteredClouds
        WeatherCodes.BROKEN_CLOUDS -> strings.core.weather.brokenClouds
        WeatherCodes.OVERCAST_CLOUDS -> strings.core.weather.overcastClouds
        WeatherCodes.LIGHT_RAIN -> strings.core.weather.lightRain
        WeatherCodes.MODERATE_RAIN -> strings.core.weather.moderateRain
        WeatherCodes.HEAVY_RAIN -> strings.core.weather.heavyRain
        WeatherCodes.VERY_HEAVY_RAIN -> strings.core.weather.veryHeavyRain
        WeatherCodes.EXTREME_RAIN -> strings.core.weather.extremeRain
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
        WeatherCodes.CLEAR_SKY -> WeatherIcons.DaySunny
        WeatherCodes.FEW_CLOUDS -> WeatherIcons.DayCloudy
        WeatherCodes.SCATTERED_CLOUDS -> WeatherIcons.DayCloudy
        WeatherCodes.BROKEN_CLOUDS -> WeatherIcons.DayCloudyHigh
        WeatherCodes.OVERCAST_CLOUDS -> WeatherIcons.DayCloudyHigh
        WeatherCodes.LIGHT_RAIN -> WeatherIcons.DaySprinkle
        WeatherCodes.MODERATE_RAIN -> WeatherIcons.DayRain
        WeatherCodes.HEAVY_RAIN -> WeatherIcons.DayShowers
        WeatherCodes.VERY_HEAVY_RAIN -> WeatherIcons.DayThunderstorm
        WeatherCodes.EXTREME_RAIN -> WeatherIcons.DaySleetStorm
        else -> WeatherIcons.DaySunny
    }
}

fun getWeatherIconNight(weatherCode: Int): ImageVector {
    return when (weatherCode) {
        WeatherCodes.CLEAR_SKY -> WeatherIcons.NightClear
        WeatherCodes.FEW_CLOUDS -> WeatherIcons.NightCloudy
        WeatherCodes.SCATTERED_CLOUDS -> WeatherIcons.NightCloudy
        WeatherCodes.BROKEN_CLOUDS -> WeatherIcons.NightCloudyHigh
        WeatherCodes.OVERCAST_CLOUDS -> WeatherIcons.NightCloudyHigh
        WeatherCodes.LIGHT_RAIN -> WeatherIcons.NightSprinkle
        WeatherCodes.MODERATE_RAIN -> WeatherIcons.NightRain
        WeatherCodes.HEAVY_RAIN -> WeatherIcons.NightShowers
        WeatherCodes.VERY_HEAVY_RAIN -> WeatherIcons.NightThunderstorm
        WeatherCodes.EXTREME_RAIN -> WeatherIcons.NightSleetStorm
        else -> WeatherIcons.NightClear
    }
}