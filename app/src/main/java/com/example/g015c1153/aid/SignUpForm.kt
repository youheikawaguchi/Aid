package com.example.g015c1153.aid

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_sign_up_form.*
import java.util.*
import java.util.regex.Pattern

class SignUpForm : AppCompatActivity() {
    private var strPW = ""              //入力PW格納用
    private var strPWRe = ""            //入力PW再入力格納用
    private var passMatch = false       //PWとPW再入力の一致判定用
    private val valueResponse = ValueResponse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_form)

        editPWRe.setOnFocusChangeListener { _, hasFocus ->
            //PW再入力のフォームからフォーカスが外れた時に発動
            if (!hasFocus) {
                strPW = editPW.text.toString()
                strPWRe = editPWRe.text.toString()

                if (strPW != strPWRe) {     //PWとPW再入力の一致を判定
                    editPW.error = valueResponse.errorPasswordMismatch  //エラー文の代入
                    editPW.error = valueResponse.errorPasswordMismatch
                    passMatch = false
                } else if (!editPW.equals("") && !editPWRe.equals("")) {    //空文字じゃないかの判定
                    passMatch = true
                }
            }
        }
    }

    fun onClick(v: View) {
        val strFN = editFirstName.text.toString()   //姓の格納用
        val strSN = editSecondName.text.toString()  //名の格納用
        val strBD = editBirthDay.text.toString()    //生年月日の格納用
        val flag = arrayOf(true, true, true, true, true)    //各項目が正しく入力されているかの判定用
        var lastFlag = true                        //画面遷移させるかの判断用

        if (!editPW.equals("") || !editFirstName.equals("") || !editSecondName.equals("")) {   //空文字判定
            val englishNumber = Pattern.compile("^[0-9a-zA-Z]+$")   //英数文字列のみ
            val notNumber = Pattern.compile("^[^0-9]+$")            //数字以外
            val password = englishNumber.matcher(strPW)
            val fn = notNumber.matcher(strFN)
            val sn = notNumber.matcher(strSN)

            val id = RadioGroupGender.checkedRadioButtonId     //チェックされたラジオボタンのIDを取得
            val radioButton = findViewById<View>(id) as RadioButton     //↑のIDをもとにRadioButtonのインスタンス化
            val strGender = radioButton.text.toString()                    //ラジオボタンのvalueを取得

            //以下文字列チェックとエラー文の表示
            if (!passMatch || strPW.equals("") || password.equals(false)) {
                flag[0] = false
                textPW.setTextColor(Color.RED)
                textPWRe.setTextColor(Color.RED)
            }
            if (strFN.equals("") || fn.equals(false)) {
                flag[1] = false
                if (strFN.equals("")) {
                    editFirstName.error = valueResponse.errorNoValue
                } else {
                    editFirstName.error = valueResponse.errorNumber
                }
            }
            if (strSN.equals("") || sn.equals(false)) {
                flag[2] = false
                if (strSN.equals("")) {
                    editSecondName.error = valueResponse.errorNoValue
                } else {
                    editSecondName.error = valueResponse.errorNumber
                }
            }
            if (strBD.equals("")) {
                flag[3] = false
                editBirthDay.error = valueResponse.errorNoValue
            }
            if (strGender.equals("")) {
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
                //インテント生成
                val mailIntent = intent
                val intent = Intent(application, SignUpCheck::class.java)
                //EditTextの中身をインテントに格納
                intent.putExtra("MailAddress", mailIntent.getStringExtra("MailAddress"))
                intent.putExtra("PW", strPW)
                intent.putExtra("PWRe", strPWRe)
                intent.putExtra("FirstName", strFN)
                intent.putExtra("SecondName", strSN)
                intent.putExtra("BirthDay", strBD)
                intent.putExtra("Gender", strGender)
                //画面遷移
                startActivity(intent)
            }
        }
    }

    //生年月日入力時のカレンダー入力用メソッド
    //(未修整)カレンダー表示ではなくスピナー表示にしたい
    fun showDatePickerDialog(v: View) {
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