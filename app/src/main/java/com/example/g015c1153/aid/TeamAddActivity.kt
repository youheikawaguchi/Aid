package com.example.g015c1153.aid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_team_add.*

class TeamAddActivity : AppCompatActivity() {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val teamAdapter = moshi.adapter(TeamData::class.java)!!
    //46行目辺り。
    //登録するチームデータを渡し、登録されたチーム情報をすべてもらう。(登録されたかの確認のため)
    private val url = ValueResponse().serverIp + ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_add)

        initSpinner()

        //チーム作成ボタンを押したときの処理
        teamAddButton.setOnClickListener {
            //入力内容の取得
            val editTeamName: String = editTeamName.text.toString()
            val editTeamDetail: String = editTeamDetail.text.toString()
            val editTeamLocal: String = local_spinner.selectedItem.toString()

            //チーム用のデータクラスに登録
            val teamDataAdd = TeamData()
            teamDataAdd.teamName = editTeamName
            teamDataAdd.teamDetail = editTeamDetail
            teamDataAdd.teamLocal = editTeamLocal
            //Realmに登録後、チームのIDを取得
//            val teamID = RealmDAO().teamAddRealm(teamDataAdd)
//            //チームをIDをもとに検索し、データベースの登録情報を取得。
//            val team = RealmDAO().teamReadRealm(teamID)
//            //次ページに値を渡せるように、Json文字列に変換
//            val teamJson = teamAdapter.toJson(team)

            //通信可能になればコメントを外す
            val toJson = teamAdapter.toJson(teamDataAdd)
            val teamID = CallOkHttp().postRun(url, toJson)   //サーバー通信、登録されたデータをもらう
            //次ページに遷移
            val teamPageIntent = Intent(this, TeamPageActivity::class.java)
            teamPageIntent.putExtra("teamID", teamID)
            startActivity(teamPageIntent)
        }
    }

    private fun initSpinner(){
        val labels = resources.getStringArray(R.array.local_list)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels)
        local_spinner.adapter = adapter

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
}