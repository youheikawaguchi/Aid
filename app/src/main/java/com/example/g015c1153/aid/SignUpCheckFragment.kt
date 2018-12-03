package com.example.g015c1153.aid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.g015c1153.aid.R
import com.example.g015c1153.aid.User
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_sign_up_check.*

class SignUpCheckFragment : Fragment() {

    private val user: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //intentから値を取り出す用にリストの作成。
        val textViews = arrayOf<TextView>(textPW, textFirstName, textSecondName, textBirthDay, textGender)   //各テキストビューをリストで取得

        //前画面からのJsonデータを受け取り、オブジェクトに変換
//        val intent = intent
//        val userDataJson = intent.getStringExtra("userData")
//        val fromJson = userAdapter.fromJson(intentUserData)


        //中身を取り出して画面に表示
//        for (i in textViews.indices) {
//            when (textViews[i]) {
//                textPW -> textViews[i].text = fromJson.password
//                textFirstName -> textViews[i].text = fromJson.firstName
//                textSecondName -> textViews[i].text = fromJson.secondName
//                textBirthDay -> textViews[i].text = fromJson.birthDay
//                textGender -> textViews[i].text = fromJson.gender
//            }
//        }

        //完了ボタンを押した時の処理
        nextFinish.setOnClickListener {
            //RealmにUser情報を登録
            RealmDAO().signUpRealmAdd(user)

            val url = "https://toridge.com/post_json.php"   //サンプル用URL
            //172.16.89.--157-- //下岡に送るためのIP(仮)のメモ。４つ目の部分は日によって変わる。
            val handler = Handler()
            //ユーザーデータをサーバーに送信
            //CallOkHttp().postRun(url, handler, userDataJson)

            //ここで画面遷移
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                     container: ViewGroup?,
                     savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_sign_up_check,
                container, false)
    }
}