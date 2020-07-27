package com.antonageev.geekbrainskotlin.ui

import android.app.Application
import com.antonageev.geekbrainskotlin.dsl.appModule
import com.antonageev.geekbrainskotlin.dsl.mainModule
import com.antonageev.geekbrainskotlin.dsl.noteModule
import com.antonageev.geekbrainskotlin.dsl.splashModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { modules(appModule, splashModule, mainModule, noteModule) }
    }
}