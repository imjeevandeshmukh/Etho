package com.bytelogs.etho

import android.app.Application
import com.bytelogs.obvious.injection.AppComponent
import com.bytelogs.obvious.injection.AppModule
import com.bytelogs.obvious.injection.DaggerAppComponent

class Etho :Application(){
    lateinit var injector: AppComponent
    override fun onCreate() {
        super.onCreate()
        injector = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()

    }
    fun getApplicationComponent(): AppComponent {
        return injector
    }

}