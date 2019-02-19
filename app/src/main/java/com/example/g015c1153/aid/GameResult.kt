package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_game_result.*

class GameResult : AppCompatActivity() {

    private val moshi  = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val tccAdapter = moshi.adapter(TeamCalendarCard::class.java)
    private val gameResultAdapter = moshi.adapter(GameData::class.java)
    private val url = ValueResponse().serverIp + ""     //パス

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_result)

        val intent = Intent()
        val resultID = intent.getStringExtra("resultID")
        val teamCalendar = TeamCalendarCard(resultID = resultID)
        val toJson = tccAdapter.toJson(teamCalendar)
        val gameResultJson = CallOkHttp().postRun(url, toJson)
        val fromJson = gameResultAdapter.fromJson(gameResultJson)
        if(fromJson != null) {
            myTeam.text = fromJson.myTeam
            otherTeam.text = fromJson.otherTeam
            myPoint.text = fromJson.myPoint
            otherPoint.text = fromJson.otherPoint
            point1.text = fromJson.myFirstHalf
            point2.text = fromJson.otherFirstHalf
            point3.text = fromJson.myLatterHalf
            point4.text = fromJson.otherLatterHalf
        }

        backButton.setOnClickListener {
            finish()
        }

    }
}
