package com.mrenann.dataagrin.core.ui.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.ES)
internal val EsStrings = Strings(
    core = CoreStrings(
        tryAgain = "Intentar de nuevo",
        weather = WeatherCodeStrings(
            clearSky = "Cielo despejado",
            fewClouds = "Pocas nubes",
            scatteredClouds = "Nubes dispersas",
            brokenClouds = "Nubes rotas",
            overcastClouds = "Nubes densas",
            lightRain = "Lluvia ligera",
            moderateRain = "Lluvia moderada",
            heavyRain = "Lluvia fuerte",
            veryHeavyRain = "Lluvia muy fuerte",
            extremeRain = "Lluvia extrema",
            unknown = "Condición desconocida",
        ),
        confirm = "Confirmar",
        cancel = "Cancelar",
        errorTitle = "Ocurrió un error"
    ),
    weather = WeatherStrings(
        tabTitle = "Clima",
        temperature = "Temperatura",
        humidity = "Humedad",
        weather = "Clima",
        hourlyForecast = "Pronóstico por hora",
        dataFromAPI = "Datos de la API",
        dataFromLocal = "Datos locales",
        currentConditions = "Condiciones actuales",
        rain = "Lluvia"
    ),
    tasks = TasksStrings(
        tabTitle = "Tareas",
        withoutTasksToday = "Sin tareas para hoy",
        withoutTasksTodaySubtitle = "No tienes tareas programadas para hoy. ¡Disfruta de tu tiempo libre!",
        tasksFinished = { finished, total -> "Tareas completadas: $finished de $total" }
    ),
    activities = ActivityStrings(
        tabTitle = "Actividades",
        newActivity = "Nueva Actividad",
        type = "Tipo",
        field = "Campo",
        notes = "Notas",
        saveBtn = "Guardar",
        startTime = "Hora de inicio",
        endTime = "Hora de finalización",
        activityHistory = "Historial de actividades",
        withoutNotes = "Sin notas",
        noActivitiesTitle = "Ninguna actividad registrada",
        noActivitiesSubtitle = "Agrega una nueva actividad para comenzar a registrar",
        today = "Hoy",
        yesterday = "Ayer",
        done = "Completado",
        delete = "Eliminar",
        pending = "Pendiente",
        inProgress = "En progreso",
        deleteConfirmationTitle = "Eliminar actividad",
        deleteConfirmationMessage = "¿Estás seguro de que deseas eliminar esta actividad?",
    )
)