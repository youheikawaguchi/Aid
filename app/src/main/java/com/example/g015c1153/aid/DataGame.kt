package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_data_game.*

class DataGame : AppCompatActivity() {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val teamDataAdapter = moshi.adapter(TeamData::class.java)
    private val gameDataAdapter = moshi.adapter(GameData::class.java)
    private var teamData = TeamData()
    private var teamDataJson = ""
    private var url = ValueResponse().serverIp + "/mGameDataAdd"     //サーバーIP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_game)

        val intent = Intent()
        teamDataJson = intent.getStringExtra("teamData")
        teamData = teamDataAdapter.fromJson(teamDataJson)!!
        val planID = intent.getStringExtra("planID")    //予定ID

        myTeam.text = teamData.teamName

        submitButton.setOnClickListener {

            val gameData = GameData()
            gameData.teamID = teamData.TeamId
            gameData.planID = planID
            gameData.myTeam = teamData.teamName
            gameData.otherTeam = otherTeam.text.toString()
            gameData.myPoint = myPoint.text.toString()
            gameData.otherPoint = otherPoint.text.toString()
            gameData.myFirstHalf = myFirstHalf.text.toString()
            gameData.otherFirstHalf = myFirstHalf.text.toString()
            gameData.myLatterHalf = myLatterHalf.text.toString()
            gameData.otherLatterHalf = otherLatterHalf.text.toString()

            val toJson = gameDataAdapter.toJson(gameData)
            CallOkHttp().postRun(url, toJson)

            onDestroy()
        }
    }
}
