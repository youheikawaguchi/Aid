package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_sign_up_check.*

class SignUpCheck : AppCompatActivity() {

    private val user: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_check)

        //intentから値を取り出す用にリストの作成。
        val textViews = arrayOf<TextView>(textPW, textPWRe, textFirstName, textSecondName, textBirthDay, textGender)   //各テキストビューをリストで取得
        val formString = arrayOf("PW", "PWRe", "FirstName", "SecondName", "BirthDay", "Gender")                        //インテントのキー値のリスト
        val intentString = arrayOfNulls<String>(formString.size)        //インテントの値を受け取る用のリスト

        val intent = intent
        //中身を取り出して画面に表示
        for (i in formString.indices) {
            intentString[i] = intent.getStringExtra(formString[i])
            textViews[i].text = intentString[i]
            when (formString[i]) {
                "PW" -> user.password = intentString[i].toString()
                "FirstName" -> user.firstName = intentString[i].toString()
                "SecondName" -> user.secondName = intentString[i].toString()
                "BirthDay" -> user.birthDay = intentString[i].toString()
                "Gender" -> user.gender = intentString[i].toString()
            }
        }
        user.mailAddress = intent.getStringExtra("MailAddress")
        //画面遷移
        nextFinish.setOnClickListener {
            //RealmにUser情報を登録
            RealmDAO().signUpRealmAdd(user)
            val loginIntent = Intent(application, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}