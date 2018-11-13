package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_team_page.*
import kotlinx.android.synthetic.main.content_team_page.*

class TeamPageActivity : AppCompatActivity() {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val teamAdapter = moshi.adapter(TeamData::class.java)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_page)
        setSupportActionBar(toolbar)

        val intent = intent
        //intentからJsonデータを取得
        val teamDataJson = intent.getStringExtra("team")
        //Jsonデータからオブジェクトに変換
        val fromJson = teamAdapter.fromJson(teamDataJson)
        val teamDataPage = TeamData()
        if (fromJson != null) {
            //JsonオブジェクトからTeamDataクラスに変換
            teamDataPage.TeamId = fromJson.TeamId
            teamDataPage.teamName = fromJson.teamName
            teamDataPage.teamDetail = fromJson.teamDetail
            teamDataPage.teamLocal = fromJson.teamLocal

            //TextViewにチームデータを書き換え
            teamNameView.text = teamDataPage.teamName
            teamDetailView.text = teamDataPage.teamDetail
            teamLocalView.text = teamDataPage.teamLocal
        }
    }
}
