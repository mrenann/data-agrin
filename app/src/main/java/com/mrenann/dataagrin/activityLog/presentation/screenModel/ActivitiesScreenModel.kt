package com.mrenann.dataagrin.activityLog.presentation.screenModel

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.mrenann.dataagrin.activityLog.domain.usecase.ActivityUseCase
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ActivityScreenModel(
    private val activityUseCase: ActivityUseCase
) : StateScreenModel<ActivityScreenModel.State>(State.Loading) {

    sealed class State {
        object Loading : State()
        data class Success(val activities: List<ActivityInfo>) : State()
        data class Error(val message: String) : State()
    }

    fun loadActivities() {
        mutableState.value = State.Loading

        screenModelScope.launch {
            activityUseCase.getAllActivities().collectLatest { resource ->
                mutableState.value = when (resource) {
                    is Resource.Loading -> State.Loading
                    is Resource.Success -> State.Success(resource.data)
                    is Resource.Error -> State.Error(resource.message)
                }
            }
        }
    }

    fun insertActivity(activity: ActivityInfo) {
        screenModelScope.launch {
            activityUseCase.insertActivity(activity).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> mutableState.value = State.Loading
                    is Resource.Success -> loadActivities()
                    is Resource.Error -> mutableState.value = State.Error(resource.message)
                }
            }
        }
    }

    fun deleteActivity(id: Int) {
        screenModelScope.launch {
            activityUseCase.deleteActivity(id).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> mutableState.value = State.Loading
                    is Resource.Success -> loadActivities()
                    is Resource.Error -> mutableState.value = State.Error(resource.message)
                }
            }
        }
    }
}
