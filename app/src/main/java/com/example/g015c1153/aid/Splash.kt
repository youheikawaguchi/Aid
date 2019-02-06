package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash.*


class Splash : AppCompatActivity() {

    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mHandler.postDelayed(mSplashTask, 1000)

        val gifMovie: Int = R.drawable.lode

        //gif画像のセット
        Glide.with(this).load(gifMovie).into(aidView)
    }

    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacks(mSplashTask)
    }

    private val mSplashTask = Runnable {
        val intent = Intent(this, TopActivity::class.java)//画面遷移のためのIntentを準備
        startActivity(intent)//実際の画面遷移を開始
        finish()//現在のActivityを削除
    }
}
