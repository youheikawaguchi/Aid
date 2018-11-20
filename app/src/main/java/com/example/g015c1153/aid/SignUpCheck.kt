package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_send.*
import kotlinx.android.synthetic.main.activity_sign_up_check.*

class SignUpCheck : AppCompatActivity() {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val userAdapter = moshi.adapter(User::class.java)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_check)

        //intentから値を取り出す用にリストの作成。
        val textViews = arrayOf<TextView>(textPW, textFirstName, textSecondName, textBirthDay, textGender)   //各テキストビューをリストで取得

        val intent = intent
        val intentUserData = intent.getStringExtra("userData")
        val fromJson = userAdapter.fromJson(intentUserData)

        val user = User()
        if(fromJson != null) {
            user.mailAddress = fromJson.mailAddress
            user.firstName = fromJson.firstName
            user.secondName = fromJson.secondName
            user.birthDay = fromJson.birthDay
            user.gender = fromJson.gender
            user.password = fromJson.password
        }

        //中身を取り出して画面に表示
        for (i in textViews.indices) {
            when (textViews[i]) {
                textPW -> textViews[i].text = user.password
                textFirstName -> textViews[i].text = user.firstName
                textSecondName -> textViews[i].text = user.secondName
                textBirthDay -> textViews[i].text = user.birthDay
                textGender -> textViews[i].text = user.gender
            }
        }

        //画面遷移
        nextFinish.setOnClickListener {
            //RealmにUser情報を登録
            RealmDAO().signUpRealmAdd(user)
            val loginIntent = Intent(application, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}