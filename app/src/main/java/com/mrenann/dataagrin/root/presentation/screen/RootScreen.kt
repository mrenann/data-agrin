package com.mrenann.dataagrin.root.presentation.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.mrenann.dataagrin.activityLog.presentation.tab.ActivityTab
import com.mrenann.dataagrin.root.presentation.components.MainContent
import com.mrenann.dataagrin.tasks.presentation.tab.TasksTab
import com.mrenann.dataagrin.weather.presentation.tab.WeatherTab

class RootScreen : Screen {
    @Composable
    override fun Content() {
        LocalNavigator.current
        val tabs = listOf(TasksTab(), ActivityTab(), WeatherTab())
        TabNavigator(
            tab = TasksTab(),
            tabDisposable = {
                TabDisposable(
                    navigator = it, tabs = tabs
                )
            }

        ) {
            MainContent(tabs)

        }
    }

}

