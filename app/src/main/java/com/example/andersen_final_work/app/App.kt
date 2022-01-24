package com.example.andersen_final_work.app

import android.app.Application
import com.example.andersen_final_work.di.AppComponent
import com.example.andersen_final_work.di.AppModule
import com.example.andersen_final_work.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}