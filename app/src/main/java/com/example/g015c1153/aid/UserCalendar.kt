package com.example.g015c1153.aid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.activity_user_calendar.*

class UserCalendar : AppCompatActivity(),UCRVHolder.ItemClickListener {

    private var mDataList: ArrayList<UserCalendarCard> = ArrayList()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val type = Types.newParameterizedType(List::class.java, UserCalendarCard::class.java)
    private val userListAdapter: JsonAdapter<List<UserCalendarCard>> = moshi.adapter(type)
    private val url = ValueResponse().serverIp + ""     //サーバーIP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_calendar)

        makeData()

        val adapter = UCCardAdapter(this,this, mDataList)
        UCRec.adapter = adapter
        UCRec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    private fun makeData() {
        val json = CallOkHttp().getRun(url)
        if (!json.isEmpty()) {
            val fromJson = userListAdapter.fromJson(json)
            if (fromJson != null) {
                for (i in 0 until fromJson.size) {
                    mDataList.add(UserCalendarCard(
                            fromJson[i].cardId,
                            fromJson[i].cardDate,
                            fromJson[i].cardTeamName,
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