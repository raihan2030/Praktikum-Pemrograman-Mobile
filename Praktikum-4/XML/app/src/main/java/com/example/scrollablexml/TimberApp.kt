package com.example.scrollablexml

import android.app.Application
import timber.log.Timber

class TimberApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}