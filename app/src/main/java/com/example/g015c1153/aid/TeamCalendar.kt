package com.example.g015c1153.aid

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.activity_member_list.*
import kotlinx.android.synthetic.main.activity_team_calendar.*

class TeamCalendar : AppCompatActivity(),TCRVHolder.ItemClickListener {

    private var mDataList: ArrayList<TeamCalendarCard> = ArrayList()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val sessionAdapter = moshi.adapter(Session::class.java)
    private val type = Types.newParameterizedType(List::class.java, TeamCalendarCard::class.java)
    private val teamCalendarAdapter: JsonAdapter<List<TeamCalendarCard>> = moshi.adapter(type)
    private val url = ValueResponse().serverIp + ""     //サーバーIP
    lateinit var pref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_calendar)

        makeData()

        val adapter = TCCardAdapter(this, this, mDataList)
        TCRec.adapter = adapter
        TCRec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun makeData() {
        pref = getSharedPreferences("Aid_Session", Context.MODE_PRIVATE)
        val teamId = pref.getString("TeamID", "Unknown")
        val session = Session(teamId = teamId!!)
        val toJson = sessionAdapter.toJson(session)
        val json = CallOkHttp().postRun(url, toJson)
        if (!json.isEmpty()) {
            val fromJson = teamCalendarAdapter.fromJson(json)
            if (fromJson != null) {
                for (i in 0 until fromJson.size) {
                    mDataList.add(TeamCalendarCard(
                            fromJson[i].cardId,
                            fromJson[i].cardDate,
                            fromJson[i].cardTitle,
                            fromJson[i].cardBody)
                    )
                }
            }
        }
    }

    override fun onItemClick(view: View, position: Int) {

    }
}