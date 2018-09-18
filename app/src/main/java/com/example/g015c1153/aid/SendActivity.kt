package com.example.g015c1153.aid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class SendActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val matcher = Matcher()
        val edit = findViewById<EditText>(R.id.editText)
        val edit2 = findViewById<EditText>(R.id.editText2)
        val getText = edit.text
        val getText2 = edit2.text

        matcher.address(getText.toString())
        matcher.domain(getText2.toString())

        val intent = Intent(this, SentActivity::class.java)
        intent.putExtra("data1", getText.toString())
        intent.putExtra("data2", getText2.toString())

        startActivity(intent)
    }
}
