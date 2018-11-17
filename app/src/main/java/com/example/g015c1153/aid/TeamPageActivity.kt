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

//        // Check that the activity is using the layout version with
//        // the fragment_container FrameLayout
//        if (findViewById<View>(R.id.fragment_container) != null) {
//
//            // However, if we're being restored from a previous state,
//            // then we don't need to do anything and should return or else
//            // we could end up with overlapping fragments.
//            if (savedInstanceState != null) {
//                return
//            }
//
//            // Create a new Fragment to be placed in the activity layout
//            val popFragment = FragmentMemberJoinPopup.newInstance("count","count2")
//            // In case this activity was started with special instructions from an
//            // Intent, pass the Intent's extras to the fragment as arguments
//            popFragment.arguments = getIntent().extras
//
//            // Add the fragment to the 'fragment_container' FrameLayout
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container_call, popFragment).commit()
//        }

        fab.setOnClickListener {
        }

        memberJoin.setOnClickListener {
//            //Fragmentに遷移用
//            val fragmentManager = supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//
//            // BackStackを設定
//            fragmentTransaction.addToBackStack(null)
//
//            // パラメータを設定
//            fragmentTransaction.replace(R.id.fragment_container_call, FragmentMemberJoinPopup.newInstance(  "fragment","fragment"))
//            fragmentTransaction.commit()

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
