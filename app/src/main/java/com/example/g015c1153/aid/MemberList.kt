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

class MemberList : AppCompatActivity(),MemberRVHolder.ItemClickListener {

    private var mDataList: ArrayList<MemberCard> = ArrayList()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val teamDataAdapter = moshi.adapter(TeamData::class.java)
    private val type = Types.newParameterizedType(List::class.java, MemberCard::class.java)
    private val memberListAdapter: JsonAdapter<List<MemberCard>> = moshi.adapter(type)
    private val url = ValueResponse().serverIp + "/mMemberList"     //サーバーIP
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)

        makeData()

        val adapter = MemberCardAdapter(this,this, mDataList)
        memberListRec.adapter = adapter
        memberListRec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    private fun makeData() {
        pref = getSharedPreferences("Aid_Session", Context.MODE_PRIVATE)
        val TeamID = pref.getString("TeamID", "Unknown")!!
        val teamData = TeamData(TeamId = TeamID)
        val toJson = teamDataAdapter.toJson(teamData)
        val json = CallOkHttp().postRun(url, toJson)
        if (!json.isEmpty()) {
            val fromJson = memberListAdapter.fromJson(json)
            if (fromJson != null) {
                for (i in 0 until fromJson.size) {
                    mDataList.add(MemberCard(
                            fromJson[i].cardId,
                            fromJson[i].cardMemberName,
                            fromJson[i].cardMemberNumber,
                            fromJson[i].cardMemberPosition)
                    )
                }
            }
        }
    }

    override fun onItemClick(view: View, position: Int) {

    }
}
