package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_sent.*

class SentActivity : AppCompatActivity() {

    private lateinit var mRealm: Realm
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val mailAdapter = moshi.adapter(LoginData::class.java)!!
    private val loginData = LoginData()
    private val url = ValueResponse().serverIp+"/re"             //サンプル用URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sent)

        //Realmのセットアップ
        val realmConfig = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        mRealm = Realm.getInstance(realmConfig)

        //インテントのテキストを取得して画面に表示
        val intent = intent

        val address = intent.getStringExtra("address")
        val domain = intent.getStringExtra("domain")
        sentAddress.text = address
        sentDomain.text = domain

        val mailAddress = "$address@$domain"

        //mailRealmAddを読んでRealmにメール情報を登録後、画面遷移
        signUpFormButton.setOnClickListener {
            loginData.mailAddress = mailAddress

            //ログインIDとパスワード(現状:固定値)を参照して画面遷移させる
            val mailJson = mailAdapter.toJson(loginData)    //KotlinオブジェクトをJSONに変換

            CallOkHttp().postRun(url, mailJson)

            //メールアドレスのアドレスとドメインを一つの文字列に変換
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}