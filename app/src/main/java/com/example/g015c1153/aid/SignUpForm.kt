package com.example.g015c1153.aid

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioButton
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_sign_up_form.*
import java.util.*

class SignUpForm : AppCompatActivity() {

    private val valueResponse = ValueResponse()
    private val matcher = Matcher()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val userAdapter = moshi.adapter(User::class.java)!!
    private lateinit var pref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_form)

        var strPW: String               //入力PW格納用
        var strPWRe: String             //入力PW再入力格納用
        var passMatch = false           //PWとPW再入力の一致判定用
        val userData = User()

        editPWRe.setOnFocusChangeListener { _, hasFocus ->
            //PW再入力のフォームからフォーカスが外れた時に発動
            if (!hasFocus) {
                strPW = editPW.text.toString()
                strPWRe = editPWRe.text.toString()
                passMatch = passMatcher(strPW,strPWRe)
            }
        }

        buttonNextCheck.setOnClickListener {
            userData.lastName = editLastName.text.toString()   //姓の格納用
            userData.firstName = editFirstName.text.toString()  //名の格納用
            userData.birthDay = editBirthDay.text.toString()    //生年月日の格納用
            val flag = arrayOf(true, true, true, true, true)    //各項目が正しく入力されているかの判定用
            var lastFlag = true                        //画面遷移させるかの判断用
            strPW = editPW.text.toString()
            strPWRe = editPWRe.text.toString()
            if (passMatcher(strPW,strPWRe)){
                userData.password = editPW.text.toString()
            }


            if (userData.password != "" || userData.lastName != "" || userData.firstName != "") {   //空文字判定

                val password = matcher.patternChecker(strPW, 2)
                val fn = matcher.patternChecker(userData.lastName, 2)
                val sn = matcher.patternChecker(userData.lastName, 3)

                val id = RadioGroupGender.checkedRadioButtonId     //チェックされたラジオボタンのIDを取得
                val radioButton = findViewById<View>(id) as RadioButton     //↑のIDをもとにRadioButtonのインスタンス化
                userData.gender = radioButton.text.toString()                    //ラジオボタンのvalueを取得

                //以下文字列チェックとエラー文の表示
                if (!passMatch || userData.password == "" || !password) {
                    flag[0] = false
                    textPW.setTextColor(Color.RED)
                    textPWRe.setTextColor(Color.RED)
                }
                if (userData.lastName == "" || !fn) {
                    flag[1] = false
                    if (userData.lastName == "") {
                        editLastName.error = valueResponse.errorNoValue
                    } else {
                        editLastName.error = valueResponse.errorNumber
                    }
                }
                if (userData.firstName == "" || !sn) {
                    flag[2] = false
                    if (userData.firstName == "") {
                        editFirstName.error = valueResponse.errorNoValue
                    } else {
                        editFirstName.error = valueResponse.errorNumber
                    }
                }
                if (userData.firstName == "") {
                    flag[3] = false
                    editBirthDay.error = valueResponse.errorNoValue
                }
                if (userData.gender == "") {
                    flag[4] = false
                    radioButton.error = valueResponse.errorNoValue
                }

                //各項目に間違いがあればフラグをlastFalseに
                for (i in flag) {
                    if (!i){
                        lastFlag = false
                    }
                }

                //正しく入力されていれば、画面遷移
                if (lastFlag) {

                    pref = getSharedPreferences("Aid_Session", Context.MODE_PRIVATE)
                    userData.mailAddress = pref.getString("UserID", "Unknown")!!

                    //Jsonに変換
                    val userJson = userAdapter.toJson(userData)
                    //インテント生成
                    val intent = Intent(application, SignUpCheck::class.java)
                    //EditTextの中身をインテントに格納
                    intent.putExtra("userData",userJson)

                    //画面遷移
                    startActivity(intent)
                }
            }
        }
    }

    private fun passMatcher(strPW:String, strPWRe: String):Boolean{

        if (strPW != strPWRe) {     //PWとPW再入力の一致を判定
            editPW.error = valueResponse.errorPasswordMismatch  //エラー文の代入
            editPW.error = valueResponse.errorPasswordMismatch
            return false
        } else if (!editPW.equals("") && !editPWRe.equals("")) {    //空文字じゃないかの判定
            return true
        }
        return false
    }

    //生年月日入力時のカレンダー入力用メソッド
    //(未修整)カレンダー表示ではなくスピナー表示にしたい
    fun showDatePickerDialog(v:View) {
        //現在の日付を取得
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, _, monthOfYear, dayOfMonth ->
            //選択した日付を検出
            val date = year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth
            editBirthDay.setText(date)
        }, year, month, day)
        dpd.show()
    }
}