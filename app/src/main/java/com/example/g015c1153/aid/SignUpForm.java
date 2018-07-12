package com.example.g015c1153.aid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpForm extends AppCompatActivity implements View.OnClickListener{

    private EditText mEditPW;
    private EditText mEditPWRe;
    private EditText mEditFirstName;
    private EditText mEditSecondName;
    private EditText mEditBirthDay;
    private RadioGroup mRadioGroupSex;
    private TextView mErrorPW;
    private TextView mTextPW;
    private TextView mTextPWRe;
    private String strPW ="";
    private String strPWRe = "";

    private boolean passMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);

        mEditPW = (EditText) findViewById(R.id.editPW);
        mEditPWRe = (EditText) findViewById(R.id.editPWRe);
        mEditFirstName = (EditText) findViewById(R.id.editFirstName);
        mEditSecondName = (EditText) findViewById(R.id.editSecondName);
        mEditBirthDay = (EditText) findViewById(R.id.editBirthDay);
        mRadioGroupSex = (RadioGroup) findViewById(R.id.RadioGroupSex);

        mErrorPW = (TextView)findViewById(R.id.errorPW);
        mTextPW = (TextView)findViewById(R.id.textPW);
        mTextPWRe = (TextView)findViewById(R.id.textPWRe);

        mEditPWRe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                    strPW = mEditPW.getText().toString();
                    strPWRe = mEditPWRe.getText().toString();
                    String result = "";

                    if(!strPW.equals(strPWRe)) {
                        result = "エラー:パスワード不一致";
                        mEditPW.setError("パスワード不一致");
                        mEditPWRe.setError("パスワード不一致");
                        passMatch = false;
                    }else if(mEditPW.length() > 0 && mEditPWRe.length() > 0){
                        result = "コンプリート";
                        passMatch = true;
                    }
                    visibility(result);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        String strFN = mEditFirstName.getText().toString();
        String strSN = mEditSecondName.getText().toString();
        String strBD = mEditBirthDay.getText().toString();
        int count = 0;
        boolean[] flag = {true,true,true,true,true,false};

        if(mEditPW.length() > 0 || mEditFirstName.length() > 0 || mEditSecondName.length() > 0) {
            Pattern englishNumber = Pattern.compile("^[0-9a-zA-Z]+$");
            Pattern english = Pattern.compile("^[^0-9]+$");
            Matcher password = englishNumber.matcher(strPW);
            Matcher fn = english.matcher(strFN);
            Matcher sn = english.matcher(strSN);

            int id = mRadioGroupSex.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(id);
            String strSex = radioButton.getText().toString();

            if (!passMatch || password.equals(false) || strPW.equals("")) {
                flag[0] = false;
                mTextPW.setTextColor(Color.RED);
                mTextPWRe.setTextColor(Color.RED);
            }
            if (strFN.equals("") || fn.equals(false)) {
                flag[1] = false;
                if(strFN.equals("")) {
                    mEditFirstName.setError("未入力です");
                }else{
                    mEditFirstName.setError("数値が入力されています");
                }
            }
            if (strSN.equals("") || sn.equals(false)) {
                flag[2] = false;
                if(strSN.equals("")){
                    mEditSecondName.setError("未入力です");
                }else {
                    mEditSecondName.setError("数値が入力されています");
                }
            }
            if (strBD.equals("")) {
                flag[3] = false;
                mEditBirthDay.setError("未入力です");
            }
            if (strSex.equals("")) {
                flag[4] = false;
                radioButton.setError("未入力です");
            }

            for (boolean lastFrag : flag) {
                if (lastFrag) {
                    count++;
                }
            }

            if (count == 5) {
                flag[5] = true;
            }

            if (flag[5]) {
                //インテント生成
                final Intent intent = new Intent(getApplication(), SignUpCheck.class);

                //EditTextの中身をインテントに格納
                intent.putExtra("PW", strPW);
                intent.putExtra("PWRe", strPWRe);
                intent.putExtra("FirstName", strFN);
                intent.putExtra("SecondName", strSN);
                intent.putExtra("BirthDay", strBD);
                intent.putExtra("Sex", strSex);
                //画面遷移
                startActivity(intent);
            }
        }
    }

    public void visibility(String text){
        mErrorPW.setVisibility(View.VISIBLE);
        mErrorPW.setText(text);
    }

    public void showDatePickerDialog(View view) {

        //現在の日付を取得
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //選択した日付を検出
                String date = year + "/" + (monthOfYear+1) + "/" + dayOfMonth;
                mEditBirthDay.setText(date);
            }
        },year, month, day);
        dpd.show();
    }
}