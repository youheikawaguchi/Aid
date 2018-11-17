package com.example.g015c1153.aid

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_top.*
import kotlinx.android.synthetic.main.app_bar_top.*
import kotlinx.android.synthetic.main.content_top.*

class TopActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, RecyclerViewHolder.ItemClickListener,TeamAdd.OnFragmentInteractionListener {

    private var mDataList : ArrayList<CardData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        setSupportActionBar(toolbar)

        //ログインからのインテントを受け取る(ログインしたかどうか)
        val visible = intent.getBooleanExtra("Switch", false)
        when (visible) {
            //ログインしていれば、マイページを表示
            true -> {
                nav_view.menu.setGroupVisible(R.id.menu_other, false)
                nav_view.menu.setGroupVisible(R.id.my_page, true)
            }
            //ログインしていなければ、新規登録を表示
            false -> {
                nav_view.menu.setGroupVisible(R.id.menu_other, true)
                nav_view.menu.setGroupVisible(R.id.my_page, false)
            }
        }

        //ドロワーの処理。デフォルトのまま。
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //カードに対する処理
        // データ作成
        makeTestData()

        // Adapter作成
        val adapter = CardAdapter(this,this, mDataList)

        // RecyclerViewにAdapterとLayoutManagerの設定
        topRecyclerView.adapter = adapter
        topRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    //表示するカードのデータを生成
    private fun makeTestData() {
        val teamDataList = RealmDAO().teamReadRealm()
        for(i in 0 until teamDataList.size){
            mDataList.add(CardData(ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher_background, null)!!,
                    teamDataList[i].teamName,
                    teamDataList[i].teamDetail))
        }
    }

    //表示するカードのデータ(チーム名検索)を生成
    private fun makeTestData(teamName:String){
        val teamDataList = RealmDAO().teamReadRealm(teamName)
        for(i in 0 until teamDataList.size){
            mDataList.add(CardData(ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher_background, null)!!,
                    teamDataList[i].teamName,
                    teamDataList[i].teamDetail))
        }
    }

    //ドロワーのアイテムを押したときの処理？デフォルトのまま
    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(applicationContext, "position $position was tapped", Toast.LENGTH_SHORT).show()
    }

    //戻るボタンを押したときのドロワーに対しての処理。デフォルトのまま
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    //右上のオプションメニューを表示させる。
    //今後削除予定
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
                makeTestData(teamData.teamName)

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

                //Fragmentに遷移用
//                val fragmentManager = supportFragmentManager
//                val fragmentTransaction = fragmentManager.beginTransaction()
//
//                // BackStackを設定
//                fragmentTransaction.addToBackStack(null)
//
//                // パラメータを設定
//                fragmentTransaction.replace(R.Id.container, TeamAdd().newInstance(  "fragment"))
//                fragmentTransaction.commit()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
