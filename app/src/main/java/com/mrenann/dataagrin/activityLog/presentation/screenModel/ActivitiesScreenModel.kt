package com.mrenann.dataagrin.activityLog.presentation.screenModel

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.mrenann.dataagrin.activityLog.domain.usecase.ActivityUseCase
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ActivityScreenModel(
    private val activityUseCase: ActivityUseCase
) : StateScreenModel<ActivityScreenModel.State>(State.Loading) {

    private var loadActivitiesJob: Job? = null

    sealed class State {
        object Loading : State()
        data class Success(val activities: List<ActivityInfo>) : State()
        data class Error(val message: String) : State()
    }

    init {
        loadActivities()
    }

    fun loadActivities() {
        loadActivitiesJob?.cancel()
        loadActivitiesJob = screenModelScope.launch {
            if (mutableState.value !is State.Success) {
                mutableState.value = State.Loading
            }

            activityUseCase.getAllActivities().collectLatest { resource ->
                mutableState.value = when (resource) {
                    is Resource.Loading -> mutableState.value
                    is Resource.Success -> State.Success(resource.data)
                    is Resource.Error -> State.Error(resource.message)
                }
            }
        }
    }

    fun insertActivity(activity: ActivityInfo) {
        val currentState = mutableState.value
        if (currentState is State.Success) {
            val updatedList = listOf(activity) + currentState.activities
            mutableState.value = State.Success(updatedList)
        }

        screenModelScope.launch {
            activityUseCase.insertActivity(activity).collectLatest { resource ->
                if (resource is Resource.Error) {
                    mutableState.value = State.Error(resource.message)
                    loadActivities()
                }
            }
        }
    }

    fun deleteActivity(id: Int) {
        val currentState = mutableState.value
        if (currentState is State.Success) {
            val updatedList = currentState.activities.filterNot { it.id == id }
            mutableState.value = State.Success(updatedList)
        }

        screenModelScope.launch {
            activityUseCase.deleteActivity(id).collectLatest { resource ->
                if (resource is Resource.Error) {
                    mutableState.value = State.Error(resource.message)
                    loadActivities()
                }
            }
        }
    }
}
