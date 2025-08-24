package com.mrenann.dataagrin.core.ui.strings

data class Strings(
    val core: CoreStrings,
    val weather: WeatherStrings,
    val tasks: TasksStrings,
    val activities: ActivityStrings,
)

data class CoreStrings(
    val tryAgain: String,
    val weather: WeatherCodeStrings,
    val confirm: String,
    val cancel: String
)

data class WeatherCodeStrings(
    val clearSky: String,
    val fewClouds: String,
    val scatteredClouds: String,
    val brokenClouds: String,
    val overcastClouds: String,
    val lightRain: String,
    val moderateRain: String,
    val heavyRain: String,
    val veryHeavyRain: String,
    val extremeRain: String,
    val unknown: String
)


data class WeatherStrings(
    val tabTitle: String,
    val temperature: String,
    val humidity: String,
    val weather: String,
    val hourlyForecast: String,
    val dataFromAPI: String,
    val dataFromLocal: String,
    val currentConditions: String,
)

data class TasksStrings(
    val tabTitle: String,
    val withoutTasksToday: String,
    val withoutTasksTodaySubtitle: String,
    val tasksFinished: (Int, Int) -> String
)

data class ActivityStrings(
    val tabTitle: String,
    val newActivity: String,
    val type: String,
    val field: String,
    val notes: String,
    val saveBtn: String,
    val startTime: String,
    val activityHistory: String,
    val endTime: String,
    val withoutNotes: String,
    val noActivitiesTitle: String,
    val noActivitiesSubtitle: String,
    val today: String,
    val yesterday: String,
    val done: String,
    val inProgress: String,
    val pending: String,
    val delete: String,
    val deleteConfirmationTitle: String,
    val deleteConfirmationMessage: String,
)