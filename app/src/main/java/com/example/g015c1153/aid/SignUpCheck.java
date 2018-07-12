package com.example.g015c1153.aid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SignUpCheck extends AppCompatActivity {

    TextView mTextPW;
    TextView mTextPWRe;
    TextView mTextFirstName;
    TextView mTextSecondName;
    TextView mBirthDay;
    TextView mSex;

    TextView[] textViews = {mTextPW,mTextPWRe,mTextFirstName,mTextSecondName,mBirthDay,mSex};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_check);

        textViews[0] = (TextView) findViewById(R.id.textPW);
        textViews[1] = (TextView) findViewById(R.id.textPWRe);
        textViews[2] = (TextView) findViewById(R.id.textFirstName);
        textViews[3] = (TextView) findViewById(R.id.textSecondName);
        textViews[4] = (TextView) findViewById(R.id.textBirthDay);
        textViews[5] = (TextView) findViewById(R.id.textSex);

        String[] formString = {"PW","PWRe","FirstName","SecondName","BirthDay","Sex"};
        String[] intentString = new String[formString.length];

        Intent intent = getIntent();
        for(int i = 0; i < formString.length; i++){
            intentString[i] = intent.getStringExtra(formString[i]);
            textViews[i].setText(intentString[i]);
        }
    }

    public void onClick(View v){
        final Intent intent = new Intent(getApplication(), LoginActivity.class);
        startActivity(intent);
    }
}
