package com.example.androidtest

import android.app.Application
import com.example.androidtest.di.component.DaggerAppComponent
import com.example.androidtest.di.module.AppModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}