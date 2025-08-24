package com.mrenann.dataagrin.activityLog.di

import com.mrenann.dataagrin.activityLog.data.repository.ActivityRepositoryImpl
import com.mrenann.dataagrin.activityLog.data.source.ActivityDataSourceImpl
import com.mrenann.dataagrin.activityLog.domain.repository.ActivityRepository
import com.mrenann.dataagrin.activityLog.domain.source.ActivityDataSource
import com.mrenann.dataagrin.activityLog.domain.usecase.ActivityUseCase
import com.mrenann.dataagrin.activityLog.domain.usecase.ActivityUseCaseImpl
import com.mrenann.dataagrin.activityLog.presentation.screenModel.ActivityScreenModel
import org.koin.dsl.module

val activityModule =
    module {
        single<ActivityDataSource> {
            ActivityDataSourceImpl(
                dao = get(),
            )
        }

        single<ActivityRepository> {
            ActivityRepositoryImpl(
                dataSource = get()
            )
        }
        single<ActivityUseCase> {
            ActivityUseCaseImpl(
                repository = get(),
            )
        }
        factory<ActivityScreenModel> {
            ActivityScreenModel(
                activityUseCase = get(),
            )
        }

    }
