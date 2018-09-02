package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_sign_up_check.*

class SignUpCheck : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_check)

        val textViews = arrayOf<TextView>(textPW, textPWRe, textFirstName, textSecondName, textBirthDay, textSex)
        val formString = arrayOf("PW", "PWRe", "FirstName", "SecondName", "BirthDay", "Sex")
        val intentString = arrayOfNulls<String>(formString.size)

        val intent = intent
        for (i in formString.indices) {
            intentString[i] = intent.getStringExtra(formString[i])
            textViews[i].text = intentString[i]
        }
    }

    fun onClick(v: View) {
        val loginIntent = Intent(application, LoginActivity::class.java)
        startActivity(loginIntent)
    }
}