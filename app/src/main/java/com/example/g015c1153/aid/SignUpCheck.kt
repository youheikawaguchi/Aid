package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
        val textViews = arrayOf<TextView>(textPW, textlastName, textfirstName, textBirthDay, textGender)   //各テキストビューをリストで取得

        val intent = intent
        val userDataJson = intent.getStringExtra("userData")
        val fromJson = userAdapter.fromJson(userDataJson)

        //中身を取り出して画面に表示
        if (fromJson != null) {
            for (i in textViews.indices) {
                when (textViews[i]) {
                    textPW -> textViews[i].text = fromJson.password
                    textlastName -> textViews[i].text = fromJson.lastName
                    textfirstName -> textViews[i].text = fromJson.firstName
                    textBirthDay -> textViews[i].text = fromJson.birthDay
                    textGender -> textViews[i].text = fromJson.gender
                }
            }
        }

        //画面遷移
        nextFinish.setOnClickListener {
            //RealmにUser情報を登録
            if (fromJson != null) {
                RealmDAO().signUpRealmAdd(fromJson)
            }

            //サーバー通信
            val url = ValueResponse().serverIp + "/re"
            //ユーザーデータをサーバーに送信
            CallOkHttp().postRun(url, userDataJson)
            val loginIntent = Intent(application, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}