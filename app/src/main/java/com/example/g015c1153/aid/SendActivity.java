package com.example.g015c1153.aid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    public void onClick(View view) {
        Login login = new Login();
        EditText edit = findViewById(R.id.editText);
        EditText edit2 = findViewById(R.id.editText2);
        Editable getText = edit.getText();
        Editable getText2 = edit2.getText();
        login.adress(String.valueOf(getText));
        login.domain(String.valueOf(getText2));
        Intent intent = new Intent(this, SentActivity.class);
        intent.putExtra("data1", getText.toString());
        intent.putExtra("data2", getText2.toString());
        //Check check = new Check();
        //check.adress("abc");
        startActivity(intent);
    }
}
