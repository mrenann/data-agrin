package com.mrenann.dataagrin.core.ui.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.PT, default = true)
internal val PtStrings = Strings(
    core = CoreStrings(
        tryAgain = "Tentar novamente",
        weather = WeatherCodeStrings(
            clearSky = "Céu limpo",
            fewClouds = "Poucas nuvens",
            scatteredClouds = "Nuvens dispersas",
            brokenClouds = "Nuvens quebradas",
            overcastClouds = "Nuvens densas",
            lightRain = "Chuva leve",
            moderateRain = "Chuva moderada",
            heavyRain = "Chuva forte",
            veryHeavyRain = "Chuva muito forte",
            extremeRain = "Chuva extrema",
            unknown = "Condição desconhecida"
        )
    ),
    weather = WeatherStrings(
        tabTitle = "Clima",
        temperature = "Temperatura",
        humidity = "Umidade",
        weather = "Clima",
        hourlyForecast = "Durante o dia",
        dataFromAPI = "Dados da API",
        dataFromLocal = "Dados Locais",
        currentConditions = "Condições Atuais",
    ),
    tasks = TasksStrings(
        tabTitle = "Tarefas",
        withoutTasksToday = "Sem tarefas para hoje",
        tasksFinished = { finished, total -> "Tarefas concluídas: $finished de $total" }
    ),
    activities = ActivityStrings(
        tabTitle = "Atividades",
        newActivity = "Nova Atividade",
        type = "Tipo",
        field = "Talhão",
        notes = "Notas",
        saveBtn = "Salvar",
        startTime = "Hora de Início",
        endTime = "Hora de Término",
        activityHistory = "Histórico de Atividades",
        withoutNotes = "Sem notas"
    )
)