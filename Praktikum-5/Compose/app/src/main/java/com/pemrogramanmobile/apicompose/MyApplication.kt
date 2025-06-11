package com.pemrogramanmobile.apicompose

import android.app.Application
import com.pemrogramanmobile.apicompose.data.local.AppDatabase

class MyApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}