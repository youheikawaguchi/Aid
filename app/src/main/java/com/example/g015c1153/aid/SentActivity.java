package com.example.g015c1153.aid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        TextView text = findViewById(R.id.textView2);
        TextView text2 = findViewById(R.id.textView4);
        Intent intent = getIntent();
        String Data1 = intent.getStringExtra("data1");
        String Data2 = intent.getStringExtra("data2");
        text.setText(Data1);
        text2.setText(Data2);
    }
    public void onClick(View v){
        Intent singUpIntent = new Intent(this,SignUpForm.class);
        startActivity(singUpIntent);
    }
}
