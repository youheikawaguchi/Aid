package com.example.g015c1153.aid

import android.app.Application

class AidApplication : Application() {

    lateinit var session : Session
    private var userID : String = "UnknownUser"
    private var teamID : String = "UnknownTeam"

    override fun onCreate() {
        super.onCreate()

    }

    fun newInstance(): Session{
        session.copy(userId = userID, teamId = teamID)
        return session
    }
}