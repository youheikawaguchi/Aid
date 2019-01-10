package com.example.g015c1153.aid

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class AidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //Realmの初期化
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}