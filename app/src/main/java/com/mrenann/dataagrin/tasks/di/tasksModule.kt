package com.mrenann.dataagrin.tasks.di

import com.mrenann.dataagrin.tasks.data.repository.TasksRepositoryImpl
import com.mrenann.dataagrin.tasks.data.source.TasksDataSourceImpl
import com.mrenann.dataagrin.tasks.data.source.TasksRemoteDataSourceImpl
import com.mrenann.dataagrin.tasks.domain.repository.TasksRepository
import com.mrenann.dataagrin.tasks.domain.source.TasksDataSource
import com.mrenann.dataagrin.tasks.domain.source.TasksRemoteDataSource
import com.mrenann.dataagrin.tasks.domain.usecase.TasksUseCase
import com.mrenann.dataagrin.tasks.domain.usecase.TasksUseCaseImpl
import com.mrenann.dataagrin.tasks.presentation.screenModel.TasksScreenModel
import org.koin.dsl.module

val tasksModule =
    module {
        single<TasksDataSource> {
            TasksDataSourceImpl(
                dao = get(),
            )
        }

        single<TasksRemoteDataSource> {
            TasksRemoteDataSourceImpl(
                service = get(),
            )
        }

        single<TasksRepository> {
            TasksRepositoryImpl(
                local = get(),
                remote = get()
            )
        }
        single<TasksUseCase> {
            TasksUseCaseImpl(
                repository = get(),
            )
        }
        factory<TasksScreenModel> {
            TasksScreenModel(
                tasksUseCase = get(),
            )
        }

    }
