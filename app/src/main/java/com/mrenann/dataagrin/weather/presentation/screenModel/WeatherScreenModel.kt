package com.mrenann.dataagrin.weather.presentation.screenModel

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.weather.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeatherScreenModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : StateScreenModel<WeatherScreenModel.State>(State.Loading) {

    sealed class State {
        object Loading : State()
        data class Success(val weather: WeatherInfo, val fromCache: Boolean) : State()
        data class Error(val message: String) : State()
    }

    fun loadWeather(forceRefresh: Boolean = false) {
        mutableState.value = State.Loading

        screenModelScope.launch {
            getWeatherUseCase(forceRefresh).collectLatest { resource ->
                mutableState.value = when (resource) {
                    is Resource.Loading -> State.Loading
                    is Resource.Success -> State.Success(
                        weather = resource.data,
                        fromCache = resource.fromCache
                    )

                    is Resource.Error -> State.Error(resource.message)
                }
            }
        }
    }
}
