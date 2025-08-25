package com.mrenann.dataagrin.core.ui.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.EN)
internal val EnStrings = Strings(
    core = CoreStrings(
        tryAgain = "Try Again",
        weather = WeatherCodeStrings(
            clearSky = "Clear Sky",
            fewClouds = "Few Clouds",
            scatteredClouds = "Scattered Clouds",
            brokenClouds = "Broken Clouds",
            overcastClouds = "Overcast Clouds",
            lightRain = "Light Rain",
            moderateRain = "Moderate Rain",
            heavyRain = "Heavy Rain",
            veryHeavyRain = "Very Heavy Rain",
            extremeRain = "Extreme Rain",
            unknown = "Unknown Condition"
        ),
        confirm = "Confirm",
        cancel = "Cancel",
        errorTitle = "An error occurred"
    ),
    weather = WeatherStrings(
        tabTitle = "Weather",
        temperature = "Temperature",
        humidity = "Humidity",
        weather = "Weather",
        hourlyForecast = "Hourly Forecast",
        dataFromAPI = "Data from API",
        dataFromLocal = "Data from Local",
        currentConditions = "Current Conditions",
        rain = "Rain"
    ),
    tasks = TasksStrings(
        tabTitle = "Tasks",
        withoutTasksToday = "No tasks for today",
        withoutTasksTodaySubtitle = "You have no tasks scheduled for today. Enjoy your free time!",
        tasksFinished = { finished, total -> "Tasks finished: $finished of $total" }
    ),
    activities = ActivityStrings(
        tabTitle = "Activities",
        newActivity = "New Activity",
        type = "Type",
        field = "Field",
        notes = "Notes",
        saveBtn = "Save",
        startTime = "Start Time",
        endTime = "End Time",
        activityHistory = "Activity History",
        withoutNotes = "No notes",
        noActivitiesTitle = "No activity recorded",
        noActivitiesSubtitle = "Add a new activity to start recording",
        today = "Today",
        yesterday = "Yesterday",
        done = "Done",
        delete = "Delete",
        pending = "Pending",
        inProgress = "In Progress",
        deleteConfirmationTitle = "Delete activity",
        deleteConfirmationMessage = "Are you sure you want to delete this activity?",
    )
)