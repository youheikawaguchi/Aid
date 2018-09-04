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
    var strPW = ""
    var strPWRe = ""
    var passMatch = false
    private val valueResponse = ValueResponse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_form)

        editPWRe.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                strPW = editPW.text.toString()
                strPWRe = editPWRe.text.toString()
                var result = ""

                if (strPW != strPWRe) {
                    result = valueResponse.errorPasswordMismatch
                    editPW.error = result
                    editPWRe.error = result
                    passMatch = false
                } else if (editPW.length() > 0 && editPWRe.length() > 0) {
                    result = valueResponse.complete
                    passMatch = true
                }
                visibility(result)
            }
        }
    }

    fun onClick(v: View) {
        val strFN = editFirstName.text.toString()
        val strSN = editSecondName.text.toString()
        val strBD = editBirthDay.text.toString()
        var count = 0
        val flag = arrayOf(true, true, true, true, true, false)

        if (editPW.length() > 0 || editFirstName.length() > 0 || editSecondName.length() > 0) {
            val englishNumber = Pattern.compile("^[0-9a-zA-Z]+$")
            val english = Pattern.compile("^[^0-9]+$")
            val password = englishNumber.matcher(strPW)
            val fn = english.matcher(strFN)
            val sn = english.matcher(strSN)

            val id = RadioGroupSex.checkedRadioButtonId
            val radioButton = findViewById<View>(id) as RadioButton
            val strSex = radioButton.text.toString()

            if (!passMatch || password.equals(false) || strPW == "") {
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
            if (strSex.equals("")) {
                flag[4] = false
                radioButton.error = valueResponse.errorNoValue
            }

            for (lastFrag in flag) {
                if (lastFrag) {
                    count++
                }
            }

            if (count == 5) {
                flag[5] = true
            }
            if (flag[5]) {
                //インテント生成
                val intent = Intent(application, SignUpCheck::class.java)

                //EditTextの中身をインテントに格納
                intent.putExtra("PW", strPW)
                intent.putExtra("PWRe", strPWRe)
                intent.putExtra("FirstName", strFN)
                intent.putExtra("SecondName", strSN)
                intent.putExtra("BirthDay", strBD)
                intent.putExtra("Sex", strSex)
                //画面遷移
                startActivity(intent)
            }
        }
    }

    fun visibility(text: String) {
        errorPW.visibility = View.VISIBLE
        errorPW.text = text
    }

    fun showDatePickerDialog(view: View) {
        //現在の日付を取得
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            //選択した日付を検出
            val date = year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth
            editBirthDay.setText(date)
        }, year, month, day)
        dpd.show()
    }
}