package com.example.androidtest

import android.app.Application
import com.example.androidtest.di.component.AppComponent
import com.example.androidtest.di.component.DaggerAppComponent
import com.example.androidtest.di.module.AppModule

class App : Application() {
    lateinit var appComponent: AppComponent
    lateinit var appModule: AppModule

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule(this)

        appComponent = DaggerAppComponent.builder()
            .appModule(appModule)
            .build()
    }
}