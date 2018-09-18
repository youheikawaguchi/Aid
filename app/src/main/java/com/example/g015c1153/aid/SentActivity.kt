package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_sent.*

class SentActivity : AppCompatActivity() {

    private lateinit var mRealm: Realm

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
        val data1 = intent.getStringExtra("data1")
        val data2 = intent.getStringExtra("data2")
        text.text = data1
        text2.text = data2

        //mailRealmAddを読んでRealmにメール情報を登録後、画面遷移
        signUpFormButton.setOnClickListener {
            //メールアドレスのアドレスとドメインを一つの文字列に変換
            val mailStr = "$data1@$data2"
            RealmDAO().mailRealmAdd(mailStr)
            val singUpIntent = Intent(this, SignUpForm::class.java)
            startActivity(singUpIntent)
        }
    }

}