package com.example.g015c1153.aid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_team_page.*
import kotlinx.android.synthetic.main.content_team_page.*

class TeamPageActivity : AppCompatActivity(), FragmentMemberJoinPopup.OnFragmentInteractionListener {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val teamAdapter = moshi.adapter(TeamData::class.java)!!
    private val sessionAdapter = moshi.adapter(Session::class.java)!!
    private lateinit var pref :SharedPreferences
    //onCreate()のmemberJoinSubmitリスナー内
    //userIDとteamIDを渡してデータを追加する。送信のみ。
    private val url = ValueResponse().serverIp + ""     //サーバーIPアドレス

    private var userID: String = ""
    private var teamID: String = ""
    private var toJson: String = ""     //teamData(IDのみ)をJson化したもの
    private var teamDataJson: String = ""   //サーバーからチーム情報を受け取る(JSON形式)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_page)
        setSupportActionBar(toolbar)

        //intentからJsonデータを取得
        teamID = pref.getString("teamID", "Unknown")!!
        val teamData = TeamData(TeamId = teamID)
        toJson = teamAdapter.toJson(teamData)
        teamDataJson = CallOkHttp().postRun(url, toJson)    //チームIDを元にチーム情報の取得
        //Jsonデータからオブジェクトに変換
        val fromJson = teamAdapter.fromJson(teamDataJson)

        if (fromJson != null) {
            //PreferenceにチームのIDを登録
            pref = getSharedPreferences("Aid_Session", Context.MODE_PRIVATE)
            pref.edit().putString("TeamID", fromJson.TeamId).apply()

            //TextViewにチームデータを書き換え
            teamNameView.text = fromJson.teamName
            teamDetailView.text = fromJson.teamDetail
            teamLocalView.text = fromJson.teamLocal
        }

        //fabボタン、メンバー追加
        fab.setOnClickListener {
            val memberAddIntent = Intent(this, MemberAdd::class.java)
            startActivity(memberAddIntent)
        }

        //チーム参加ボタン
        memberJoin.setOnClickListener {
            val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.fragment_member_join_popup,null)
            val popupWindow = PopupWindow(
                    view,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }

            val memberJoinSubmit = view.findViewById<Button>(R.id.member_join_submit)

            //チームに参加ボタン
            memberJoinSubmit.setOnClickListener {
                pref = getSharedPreferences("Aid_Session", Context.MODE_PRIVATE)
                userID = pref.getString("UserID", "Unknown")!!
                teamID = pref.getString("TeamID", "Unknown")!!
                if(userID != "Unknown" && teamID != "Unknown") {
                    val session = Session(userID, teamID)
                    val toJson = sessionAdapter.toJson(session)
                    CallOkHttp().postRun(url, toJson)           //UserIDとTeamIDをサーバーに渡して、データを追加する
                } else {
                    Toast.makeText(this, "登録できませんでした", Toast.LENGTH_LONG).show()
                }
                popupWindow.dismiss()
            }

            TransitionManager.beginDelayedTransition(fragment_container_call)
            popupWindow.showAtLocation(
                    fragment_container_call,
                    Gravity.CENTER,
                    0,
                    0
            )
        }

        //メンバーリストボタン
        memberListButton.setOnClickListener {
            val memberListIntent = Intent(this, MemberList::class.java)
            startActivity(memberListIntent)
        }

        //チームカレンダーボタン
        teamCalendarButton.setOnClickListener {
            val teamCalendarIntent = Intent(this, TeamCalendar::class.java)
            teamCalendarIntent.putExtra("teamData", teamDataJson)
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
