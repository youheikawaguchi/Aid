package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_top.*
import kotlinx.android.synthetic.main.app_bar_top.*
import kotlinx.android.synthetic.main.content_top.*

class TopActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, RecyclerViewHolder.ItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        setSupportActionBar(toolbar)

        //val hoges = resources.getStringArray(R.array.hoges).toMutableList()

        //カードビューの数(現状:固定値)
        val list = mutableListOf("1", "2", "3", "4")

        //RecyclerViewに対する処理
        topRecyclerView.adapter = CardAdapter(this, this, list)     //アダプターにカードビューをセット
        topRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)  //LinerLayout(縦)に指定


        //ログインからのインテントを受け取る(ログインしたかどうか)
        val visible = intent.getBooleanExtra("switch", false)
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
        menuInflater.inflate(R.menu.top, menu)
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
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
