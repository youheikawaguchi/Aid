package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.activity_team_calendar.*

class TeamCalendar : AppCompatActivity(),TCRVHolder.ItemClickListener {

    private var mDataList: ArrayList<TeamCalendarCard> = ArrayList()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val teamDataAdapter = moshi.adapter(TeamData::class.java)
    private val sessionAdapter = moshi.adapter(Session::class.java)
    private val type = Types.newParameterizedType(List::class.java, TeamCalendarCard::class.java)
    private val teamCalendarAdapter: JsonAdapter<List<TeamCalendarCard>> = moshi.adapter(type)
    private val url = ValueResponse().serverIp + ""     //サーバーIP
    private var teamID: String = ""
    private var teamDataJson = ""
    private var teamData:TeamData = TeamData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_calendar)

        val intent = Intent()
        teamDataJson = intent.getStringExtra("teamData")
        teamData = teamDataAdapter.fromJson(teamDataJson)!!

        teamID = teamData.TeamId
        makeData()

        val adapter = TCCardAdapter(this, this, mDataList)
        TCRec.adapter = adapter
        TCRec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onRestart() {
        super.onRestart()

        makeData()
    }

    private fun makeData() {
        val session = Session(teamId = teamID)
        val toJson = sessionAdapter.toJson(session)
        val json = CallOkHttp().postRun(url, toJson)
        if (!json.isEmpty()) {
            val fromJson = teamCalendarAdapter.fromJson(json)
            if (fromJson != null) {
                for (i in 0 until fromJson.size) {
                    mDataList.add(TeamCalendarCard(
                            fromJson[i].cardId,
                            fromJson[i].cardStartDate,
                            fromJson[i].cardEndDate,
                            fromJson[i].cardTitle,
                            fromJson[i].cardBody,
                            fromJson[i].resultID)
                    )
                }
            }
        }
    }

    override fun onItemClick(view: View, position: Int) {

        //resultIDの有無を確認
        if(mDataList[position].resultID.isEmpty()) {
            //resultIDがなければDataGameへ。mDataList[position]のJSON(チーム情報)とcardID(予定ID)を渡す。
            val dataGameIntent = Intent(this, DataGame::class.java)
            dataGameIntent.putExtra("teamData", teamDataJson)
            dataGameIntent.putExtra("planID", mDataList[position].cardId)
            startActivity(dataGameIntent)
        }else{
            //resultIDがあればGameResultへ。チーム情報とresultIDを渡す。
            val gameResultIntent = Intent(this, GameResult::class.java)
            gameResultIntent.putExtra("temData",teamDataJson)
            gameResultIntent.putExtra("resultID", mDataList[position].resultID)
            startActivity(gameResultIntent)
        }
    }
}