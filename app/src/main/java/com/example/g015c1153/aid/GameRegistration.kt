package com.example.g015c1153.aid

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_game_registration.*
import kotlinx.android.synthetic.main.activity_sign_up_form.*
import java.util.*

class GameRegistration : AppCompatActivity  () {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val gameAdapter = moshi.adapter(Game::class.java)!!
    private val url = ValueResponse().serverIp + ""     //サーバーIP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_registration)

        registrationBotton2.setOnClickListener {
            val game = Game()
            game.startDate = dayText2.toString()
            game.endDate = timeText2.toString()
            game.place = placeText2.toString()
            game.memo = editText.toString()
            game.otherTeam = otherTeam.toString()

            val toJson = gameAdapter.toJson(game)
            CallOkHttp().postRun(url, toJson)
            finish()
        }

        backBotton2.setOnClickListener {
            finish()
        }
    }

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
