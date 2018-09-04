package com.example.g015c1153.aid;

import android.support.v7.app.AppCompatActivity;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
//Login(){
//}

    public boolean adress(String adress) {
        Pattern adpattern = Pattern.compile("^[0-9]+$");

        if (adress.length() <= 0) {
            return true;
        } else if (adpattern.matcher(adress).find()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean domain(String domain) {
        Pattern dopattern = Pattern.compile("[0-9]");

        if (domain.length() <= 0) {
            return true;
        } else if (dopattern.matcher(domain).find()) {
            return true;
        } else {
            return false;
        }
    }
}
