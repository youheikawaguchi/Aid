package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sent.*
import okhttp3.*
import java.io.IOException

class SentActivity : AppCompatActivity() {

    private lateinit var mRealm: Realm
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val mailAdapter = moshi.adapter(LoginData::class.java)!!
    private val loginData = LoginData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sent)

        //Realmのセットアップ
        val realmConfig = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        mRealm = Realm.getInstance(realmConfig)

        //インテントのテキストを取得して画面に表示
        val text = findViewById<TextView>(R.id.textView2)
        val text2 = findViewById<TextView>(R.id.textView4)
        val intent = intent


        val address = intent.getStringExtra("data1")
        val domain = intent.getStringExtra("data2")
        text.text = address
        text2.text = domain

        val mailAddress = "$address@$domain"

        //mailRealmAddを読んでRealmにメール情報を登録後、画面遷移
        signUpFormButton.setOnClickListener {
            loginData.mailAddress = mailAddress

            //ログインIDとパスワード(現状:固定値)を参照して画面遷移させる
            val mailJson = mailAdapter.toJson(loginData)  //KotlinオブジェクトをJSONに変換
            val url = "https://172.20.10.9/repeater"   //サンプル用URL
            //172.16.89.--157-- //下岡に送るためのIP(仮)のメモ。４つ目の部分は日によって変わる。
            val handler = Handler()
            run(url, handler, mailJson)

            //メールアドレスのアドレスとドメインを一つの文字列に変換
            val signUpIntent = Intent(this, SignUpForm::class.java)
            signUpIntent.putExtra("mailAddress", mailAddress)
            startActivity(signUpIntent)
        }
    }

    private fun run(url: String, handler: Handler, json: String) {

        val postBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val request = Request.Builder()
                .addHeader("Content-Type", "text/plain; charset=utf-8")
                .url(url)
                .post(postBody)
                .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
            }
        })
    }

}