package com.example.androidapidemo

import android.app.Application
import com.example.androidapidemo.data.ApiDatabase
import com.example.androidapidemo.data.ApiRepository

class ApiDemoApplication : Application() {
    val database by lazy { ApiDatabase.getDatabase(this) }
    val repository by lazy { ApiRepository(database.apiItemDao()) }
} 