package com.example.g015c1153.aid

import android.app.Application
import io.realm.Realm

class AidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}