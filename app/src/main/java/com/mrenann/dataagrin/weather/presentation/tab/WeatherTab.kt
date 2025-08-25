package com.mrenann.dataagrin.weather.presentation.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mrenann.dataagrin.core.ui.presentation.components.ErrorView
import com.mrenann.dataagrin.weather.presentation.components.WeatherContent
import com.mrenann.dataagrin.weather.presentation.screenModel.WeatherScreenModel
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Sun

class WeatherTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = strings.weather.tabTitle
            val icon = rememberVectorPainter(EvaIcons.Fill.Sun)
            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<WeatherScreenModel>()
        val state by screenModel.state.collectAsState()

        val isRefreshing = (state as? WeatherScreenModel.State.Success)?.isRefreshing ?: false
        val pullRefreshState = rememberPullToRefreshState()

        PullToRefreshBox(
            state = pullRefreshState,
            onRefresh = { screenModel.loadWeather(forceRefresh = true) },
            modifier = Modifier.fillMaxSize(),
            isRefreshing = isRefreshing,
        ) {
            when (val s = state) {
                is WeatherScreenModel.State.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is WeatherScreenModel.State.Error -> {
                    ErrorView(
                        message = s.message,
                        onRetry = { screenModel.loadWeather(forceRefresh = true) }
                    )
                }

                is WeatherScreenModel.State.Success -> {
                    WeatherContent(weather = s.weather, fromCache = s.fromCache)
                }
            }
        }
    }
}