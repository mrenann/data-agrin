package com.mrenann.dataagrin.weather.presentation.screenModel

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.weather.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeatherScreenModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : StateScreenModel<WeatherScreenModel.State>(State.Loading) {

    private var loadWeatherJob: Job? = null

    sealed class State {
        object Loading : State()
        data class Success(
            val weather: WeatherInfo,
            val fromCache: Boolean,
            val isRefreshing: Boolean = false
        ) : State()

        data class Error(val message: String) : State()
    }

    init {
        loadWeather()
    }

    fun loadWeather(forceRefresh: Boolean = false) {
        loadWeatherJob?.cancel()

        loadWeatherJob = screenModelScope.launch {
            val currentState = mutableState.value
            if (currentState is State.Success && forceRefresh) {
                mutableState.value = currentState.copy(isRefreshing = true)
            } else if (currentState !is State.Success) {
                mutableState.value = State.Loading
            }

            getWeatherUseCase(forceRefresh).collectLatest { resource ->
                val newState = when (resource) {
                    is Resource.Loading -> mutableState.value
                    is Resource.Success -> State.Success(
                        weather = resource.data,
                        fromCache = resource.fromCache,
                        isRefreshing = false
                    )

                    is Resource.Error -> State.Error(resource.message)
                }
                mutableState.value = newState
            }
        }
    }
}