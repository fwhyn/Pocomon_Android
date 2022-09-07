package com.fwhyn.pocomon

import android.app.Application
import com.fwhyn.pocomon.data.di.dataModule
import com.fwhyn.pocomon.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PocomonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PocomonApp)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}