package com.example.g015c1153.aid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_send.*


class SendActivity : AppCompatActivity(){
    // キーボード表示を制御するためのオブジェクト
    var inputMethodManager: InputMethodManager? = null
    // 背景のレイアウト
    private var mainLayout: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        mainLayout = findViewById(R.id.Layout)

        button.setOnClickListener{
            val matcher = Matcher()
            val address = date.text.toString()
            val domain = editText2.text.toString()

            if(matcher.patternChecker(address, 0) && matcher.patternChecker(domain, 1)) {
                val intent = Intent(this, SentActivity::class.java)
                intent.putExtra("address", address)
                intent.putExtra("domain", domain)

                startActivity(intent)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        // キーボードを隠す
        inputMethodManager?.hideSoftInputFromWindow(mainLayout?.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS)
        // 背景にフォーカスを移す
        mainLayout?.requestFocus()

        return true

    }
}