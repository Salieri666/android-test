package com.example.androidtest.di.component

import android.app.Application
import android.content.Context
import com.example.androidtest.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun context(): Context
    fun applicationContext(): Application
}