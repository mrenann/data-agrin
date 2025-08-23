package com.mrenann.dataagrin

import android.app.Application
import android.util.Log
import com.mrenann.dataagrin.root.di.homeModule
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
                    homeModule
                )
            } catch (e: KoinApplicationAlreadyStartedException) {
                Log.e("Koin", "Koin already started", e)
            }
        }
    }
}
