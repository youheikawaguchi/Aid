package com.example.g015c1153.aid

import android.content.Context
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
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_team_page.*
import kotlinx.android.synthetic.main.content_team_page.*

class TeamPageActivity : AppCompatActivity(), FragmentMemberJoinPopup.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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

        fab.setOnClickListener {
        }

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

            memberJoinSubmit.setOnClickListener {
                
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
    }
}
