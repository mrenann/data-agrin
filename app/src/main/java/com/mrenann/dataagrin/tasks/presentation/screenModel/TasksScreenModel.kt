package com.mrenann.dataagrin.tasks.presentation.screenModel

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import com.mrenann.dataagrin.core.domain.model.ActivityInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.tasks.domain.usecase.TasksUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TasksScreenModel(
    private val tasksUseCase: TasksUseCase
) : StateScreenModel<TasksScreenModel.State>(State.Loading) {

    sealed class State {
        object Loading : State()
        data class Success(val tasks: List<ActivityInfo>) : State()
        data class Error(val message: String) : State()
    }

    fun loadTasksByDate(date: Long) {
        mutableState.value = State.Loading

        screenModelScope.launch {
            tasksUseCase.getTasksByDate(date).collectLatest { resource ->
                mutableState.value = when (resource) {
                    is Resource.Loading -> State.Loading
                    is Resource.Success -> State.Success(resource.data)
                    is Resource.Error -> State.Error(resource.message)
                }
            }
        }
    }

    fun updateTaskStatus(id: Int, status: ActivityStatus) {
        val currentState = mutableState.value
        if (currentState is State.Success) {
            val updatedTasks = currentState.tasks.map { task ->
                if (task.id == id) {
                    task.copy(status = status)
                } else {
                    task
                }
            }
            mutableState.value = State.Success(updatedTasks)
        }

        screenModelScope.launch {
            tasksUseCase.updateStatus(id, status).collectLatest { resource ->
                if (resource is Resource.Error) {
                    mutableState.value = State.Error(resource.message)
                }
            }
        }
    }
}
