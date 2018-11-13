package com.example.g015c1153.aid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_send.*

class SendActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val matcher = Matcher()
        val address = editText.text.toString()
        val domain = editText2.text.toString()

        if(matcher.address(address) && matcher.domain(domain)) {

            val intent = Intent(this, SentActivity::class.java)
            intent.putExtra("data1", address)
            intent.putExtra("data2", domain)

            startActivity(intent)
        }
    }
}
