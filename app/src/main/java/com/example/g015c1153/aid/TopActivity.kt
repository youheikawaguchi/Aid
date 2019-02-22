package com.example.g015c1153.aid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.activity_top.*
import kotlinx.android.synthetic.main.app_bar_top.*
import kotlinx.android.synthetic.main.content_top.*
import java.net.URL

class TopActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        RecyclerViewHolder.ItemClickListener{

    private var mDataList : ArrayList<CardData> = ArrayList()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val userAdapter = moshi.adapter(User::class.java)
    private val teamAdapter = moshi.adapter(TeamData::class.java)
    private val type = Types.newParameterizedType(List::class.java, TeamData::class.java)
    private val teamListAdapter: JsonAdapter<List<TeamData>> = moshi.adapter(type)
    private lateinit var pref : SharedPreferences
    private var teamID: String? = ""
    private var teamDataList: List<TeamData>? = null

    //サーバー通信は、makeData()でget, makeData(String), onItemClick(View,Int)でpostを行っている
    //チーム情報 = ID, 名前, チーム概要, 地域
    //makeData() = DBに登録されてあるチーム情報をすべて。
    //makeData(String) = チーム名情報のみのJsonデータをもとに、マッチするチーム情報をすべて。
    //onCreate()内冒頭、when式内で使用。UserIDをもとに、所属チーム情報を取得。
    private val makeDataURL = ValueResponse().serverIp + "/mTop2"     //makeData()用パス、
    private val makeDataSearchURL = ValueResponse().serverIp + "/mTopTeam"   //makeData(String)用パス。
    private val joinTeamURL = ValueResponse().serverIp + "/mTopUser"     //ユーザーIDを元に所属チーム情報を取得用パス

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        setSupportActionBar(toolbar)

        //ログインからのインテントを受け取る(ログインしたかどうか)
        pref = getSharedPreferences("Aid_Session", Context.MODE_PRIVATE)
        val loginID = pref.getString("UserID", "Unknown")
        if (loginID != "Unknown" && loginID != "") {
        //ログインしていれば、チーム作成、所属チームを表示
//            val user = User(id = loginID!!)
//            val toJson = userAdapter.toJson(user)
//            val teamDataJson = CallOkHttp().postRun(joinTeamURL, toJson)
//            teamDataList = teamListAdapter.fromJson(teamDataJson)
//            addNewItem(teamDataList)
            nav_view.menu.setGroupVisible(R.id.menu_other, false)
            nav_view.menu.setGroupVisible(R.id.menu_main, true)
        }
        //ログインしていなければ、新規登録を表示
        else {
            nav_view.menu.setGroupVisible(R.id.menu_other, true)
            nav_view.menu.setGroupVisible(R.id.menu_main, false)
        }

        //ドロワーの処理。デフォルトのまま。
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        //カードに対する処理
        // データ作成
        makeData()

        // Adapter作成
        val adapter = CardAdapter(this,this, mDataList)

        // RecyclerViewにAdapterとLayoutManagerの設定
        topRecyclerView.adapter = adapter
        topRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    override fun onRestart() {
        super.onRestart()
        makeData()
        // Adapter作成
        val adapter = CardAdapter(this,this, mDataList)

        // RecyclerViewにAdapterとLayoutManagerの設定
        topRecyclerView.adapter = adapter
        topRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    //表示するカードのデータを生成
    private fun makeData() {
//        val teamDataList = RealmDAO().teamReadRealm()
        mDataList.clear()
        val json = CallOkHttp().getRun(makeDataURL)
        if(!json.isEmpty()) {
            val fromJson = teamListAdapter.fromJson(json)
            if (fromJson != null) {
                for (i in 0 until fromJson.size) {
                    var bmp :Bitmap
                    if(fromJson[i].teamLogo != "no_image") {
//                        val imageURL = URL(fromJson[i].teamLogo)
//                        val stream = imageURL.openStream()
//                        bmp = BitmapFactory.decodeStream(stream)
//
//                        mDataList.add(CardData(
//                                fromJson[i].TeamId,
//                                bmp,
//                                fromJson[i].teamName,
//                                fromJson[i].teamDetail)
//                        )
                    }else{
                        mDataList.add(CardData(
                                fromJson[i].TeamId,
                                ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher_background, null)!!,
                                fromJson[i].teamName,
                                fromJson[i].teamDetail)
                        )
                    }
                }
            }
        }
    }

    //表示するカードのデータ(チーム名検索)を生成
    private fun makeData(teamName:String){
//        val teamDataList = RealmDAO().teamReadRealm(teamName)
        val teamData = TeamData(teamName = teamName)
        val toJson = teamAdapter.toJson(teamData)
        val json = CallOkHttp().postRun(makeDataSearchURL, toJson)
        if(!json.isEmpty()) {
            val fromJson = teamListAdapter.fromJson(json)
            if (fromJson != null) {
                for (i in 0 until fromJson.size) {
                    var bmp :Bitmap
                    if(fromJson[i].teamLogo != "no_image") {
//                        val imageURL = URL(fromJson[i].teamLogo)
//                        val stream = imageURL.openStream()
//                        bmp = BitmapFactory.decodeStream(stream)
//
//                        mDataList.add(CardData(
//                                fromJson[i].TeamId,
//                                bmp,
//                                fromJson[i].teamName,
//                                fromJson[i].teamDetail)
//                        )
                    }else{
                        mDataList.add(CardData(
                                fromJson[i].TeamId,
                                ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher_background, null)!!,
                                fromJson[i].teamName,
                                fromJson[i].teamDetail)
                        )
                    }
                }
            }
        }
    }

    //カードビューを押したときの処理
    override fun onItemClick(view: View, position: Int) {
        val teamIntent = Intent(application, TeamPageActivity::class.java)

        //カードのポジションをもとに、カードのID(チーム名)を取得し、サーバーへ検索をかける
        teamID = mDataList[position].cardUserId
        pref = getSharedPreferences("Aid_Session", Context.MODE_PRIVATE)
        pref.edit().putString("TeamID", teamID).apply()
        startActivity(teamIntent)
    }

    //戻るボタンを押したときのドロワーに対しての処理。デフォルトのまま
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    //ツールバーのメニュー表示。検索ボタンとか。
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.top, menu)
        //検索バーを追加
        menuInflater.inflate(R.menu.search_view_menu, menu)

        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        // イベント
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // クエリーを送信
            override fun onQueryTextSubmit(query: String): Boolean {
                // アプリタイトルに現在の値を表示する
                //this@TopActivity.title = query
                val teamData = TeamData()
                teamData.teamName = query
                mDataList.clear()
                if(!teamData.teamName.isEmpty()){
                    makeData(teamData.teamName)
                }else {
                    makeData()
                }

                topRecyclerView.adapter = CardAdapter(this@TopActivity, this@TopActivity, mDataList)   //アダプターにカードビューをセット
                return false
            }

            // クエリーを編集中
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    //オプションメニューのアイテムをクリックしたときの処理
    //今後削除予定
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //ドロワーのアイテムを押したときの処理。基本的に画面遷移のみ。
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.send -> {
                val sendIntent = Intent(this, SendActivity::class.java)
                startActivity(sendIntent)
            }
            R.id.login -> {
                val loginIntent = Intent(application, LoginActivity::class.java)
                startActivity(loginIntent)
            }
            R.id.teamAdd -> {
                val teamAddIntent = Intent(this, TeamAddActivity::class.java)
                startActivity(teamAddIntent)
            }
            else -> {
                Toast.makeText(applicationContext, "${item.title}", Toast.LENGTH_LONG).show()
                pref = getSharedPreferences("Aid_Session", Context.MODE_PRIVATE)
                pref.edit().putString("TeamID", teamDataList!![item.itemId].TeamId).apply()
                val teamPageIntent = Intent(this, TeamPageActivity::class.java)
                startActivity(teamPageIntent)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun addNewItem(teamDataList: List<TeamData>?): Boolean{


        val menu = nav_view.menu
        if (teamDataList != null) {
            for(i in 1.. teamDataList.size){
                menu.add(R.id.menu_main, i, Menu.NONE, teamDataList[i].teamName)
            }
        }
        return true
    }
}