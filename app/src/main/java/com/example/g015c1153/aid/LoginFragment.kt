package com.example.g015c1153.aid
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import java.io.IOException

class LoginFragment : Fragment() {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val loginAdapter = moshi.adapter(LoginData::class.java)!!
    private var loginData = LoginData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.activity_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //サインアップボタン処理
        view.findViewById<Button>(R.id.sign_up_button).setOnClickListener {
            loginData.mailAddress = userName.text.toString()
            loginData.password = password.text.toString()
            //ログインIDとパスワード(現状:固定値)を参照して画面遷移させる

            //サーバー通信
            val loginJson = loginAdapter.toJson(loginData)  //KotlinオブジェクトをJSONに変換
            val url = ValueResponse().serverIp + "/reLogin"   //サンプル用URL
            val loginResult = CallOkHttp().postRun(url, loginJson)    //HTTP通信を行う

            //(未修整)DBに接続した結果に従って画面遷移の判定を行う
            if (loginResult != null) {
                if (!loginResult.isEmpty()) {
                    val topIntent = Intent(context, TopActivity::class.java)
                    topIntent.putExtra("Switch", true)
                    startActivity(topIntent)
                } else if (loginJson.isEmpty()) {
                    val signUpIntent = Intent(context, SignUpForm::class.java)
                    signUpIntent.putExtra("MailAddress", loginData.mailAddress)
                    startActivity(signUpIntent)

                    /* val loginIntent = Intent(context, SignUpForm::class.java)
                     loginIntent.putExtra("info",list)
                     startActivity(loginIntent)*/
                }
            }
        }
        //サインインボタン処理
        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            val topIntent = Intent(context, TopActivity::class.java)
            startActivity(topIntent)
        }
    }

    //HTTP通信処理用メソッド
    //現状、POST用URLにアクセスし、JSONコードを送り、URL先にJSONコードが記述されるので
    //Webページのbody部分(JSONコードしか書いてない)を取得して、取得したJSONコード(文字列)を画面に表示している

}