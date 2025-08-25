package com.mrenann.dataagrin

import android.app.Application
import android.util.Log
import com.mrenann.dataagrin.activityLog.di.activityModule
import com.mrenann.dataagrin.core.di.firebaseModule
import com.mrenann.dataagrin.core.di.networkModule
import com.mrenann.dataagrin.core.di.roomModule
import com.mrenann.dataagrin.root.di.homeModule
import com.mrenann.dataagrin.tasks.di.tasksModule
import com.mrenann.dataagrin.weather.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.error.KoinApplicationAlreadyStartedException

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            try {
                androidContext(applicationContext)
                modules(
                    homeModule,
                    networkModule,
                    roomModule,
                    weatherModule,
                    activityModule,
                    tasksModule,
                    firebaseModule
                )
            } catch (e: KoinApplicationAlreadyStartedException) {
                Log.e("Koin", "Koin already started", e)
            }
        }
    }
}
