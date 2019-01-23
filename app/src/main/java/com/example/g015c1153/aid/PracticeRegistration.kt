package com.example.g015c1153.aid

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_practice_registration.*
import kotlinx.android.synthetic.main.activity_sign_up_form.*
import java.util.*

class PracticeRegistration : AppCompatActivity() {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val practiceAdapter = moshi.adapter(Practice::class.java)!!
    private val practice = Practice()
    private val url = ValueResponse().serverIp + ""     //サーバーIP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_registration)

        dayText.setOnClickListener {
            showDatePickerDialog()
        }
        timeText.setOnClickListener {
            showDatePickerDialog()
        }

        registration.setOnClickListener {
            practice.startDate = dayText.text.toString()
            practice.endDate = timeText.text.toString()
            practice.place = placeText.text.toString()
            practice.memo = memoText.text.toString()

            val toJson = practiceAdapter.toJson(practice)
            CallOkHttp().postRun(url, toJson)
            finish()
        }

        back.setOnClickListener {
            finish()
        }
    }

    private fun showDatePickerDialog() {
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
