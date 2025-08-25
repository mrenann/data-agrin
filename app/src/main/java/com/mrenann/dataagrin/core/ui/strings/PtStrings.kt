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
        ),
        confirm = "Confirmar",
        cancel = "Cancelar",
        errorTitle = "Ocorreu um erro"
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
        rain = "Chuva"
    ),
    tasks = TasksStrings(
        tabTitle = "Tarefas",
        withoutTasksToday = "Sem tarefas para hoje",
        withoutTasksTodaySubtitle = "Você não tem tarefas agendadas para hoje. Aproveite seu tempo livre!",
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
        withoutNotes = "Sem notas",
        noActivitiesTitle = "Nenhuma atividade registrada",
        noActivitiesSubtitle = "Adicione uma nova atividade para começar a registrar",
        today = "Hoje",
        yesterday = "Ontem",
        done = "Concluído",
        delete = "Excluir",
        pending = "Pendente",
        inProgress = "Andamento",
        deleteConfirmationTitle = "Excluir atividade",
        deleteConfirmationMessage = "Tem certeza de que deseja excluir esta atividade?",
    )
)