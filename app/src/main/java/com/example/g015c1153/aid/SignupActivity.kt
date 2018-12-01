package com.example.g015c1153.aid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // BackStackを設定
            fragmentTransaction.addToBackStack(null)

            // counterをパラメータとして設定
            val count = 0
            fragmentTransaction.add(R.id.Kawaguti, SendFragment.newInstance(count))

            fragmentTransaction.commit()
        }

    }
}
