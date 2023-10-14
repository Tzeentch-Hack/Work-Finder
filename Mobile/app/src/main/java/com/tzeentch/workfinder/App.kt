package com.tzeentch.workfinder

import android.app.Application
import com.tzeentch.workfinder.remote.businessModule
import com.tzeentch.workfinder.remote.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.ERROR )
            androidContext(androidContext = this@App)
            modules(networkModule(), businessModule())
        }
    }
}