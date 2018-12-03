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
        val textViews = arrayOf<TextView>(textPW, textFirstName, textSecondName, textBirthDay, textGender)   //各テキストビューをリストで取得

        val intent = intent
        val userDataJson = intent.getStringExtra("userData")
        val fromJson = userAdapter.fromJson(userDataJson)

        //中身を取り出して画面に表示
        if (fromJson != null) {
            for (i in textViews.indices) {
                when (textViews[i]) {
                    textPW -> textViews[i].text = fromJson.password
                    textFirstName -> textViews[i].text = fromJson.firstName
                    textSecondName -> textViews[i].text = fromJson.secondName
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
            val url = "https://toridge.com/post_json.php"   //サンプル用URL
            //172.16.89.--157-- //下岡に送るためのIP(仮)のメモ。４つ目の部分は日によって変わる。
            val handler = Handler()
            //ユーザーデータをサーバーに送信
            CallOkHttp().postRun(url, handler, userDataJson)
            val loginIntent = Intent(application, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}