package com.example.g015c1153.aid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_send.*

class SendActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)

        button.setOnClickListener{
            val matcher = Matcher()
            val address = editText.text.toString()
            val domain = editText2.text.toString()

            if(matcher.patternChecker(address, 0) && matcher.patternChecker(domain, 1)) {
                val intent = Intent(this, SentActivity::class.java)
                intent.putExtra("address", address)
                intent.putExtra("domain", domain)

                startActivity(intent)
            }
        }
    }
}
