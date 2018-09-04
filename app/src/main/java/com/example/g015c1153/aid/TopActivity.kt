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

class TopActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, RecyclerViewHolder.ItemClickeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        setSupportActionBar(toolbar)

        //val hoges = resources.getStringArray(R.array.hoges).toMutableList()

        val list = mutableListOf("1", "2", "3", "4")

        topRecyclerView.adapter = CardAdapter(this, this, list)
        topRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

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

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(applicationContext, "position $position was tapped", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.signUp -> {
                val formIntent = Intent(this, SignUpForm::class.java)
                startActivity(formIntent)
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
