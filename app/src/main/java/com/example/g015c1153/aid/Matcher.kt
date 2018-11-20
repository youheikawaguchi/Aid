package com.example.g015c1153.aid

import android.support.v7.app.AppCompatActivity
import java.util.regex.Pattern

class Matcher : AppCompatActivity() {

    fun patternChecker(str:String, num: Int):Boolean{
        var pattern: Pattern? = null
        when(num){
            0 ->
                pattern = Pattern.compile("^[0-9]+$")
            1 -> 
                pattern = Pattern.compile("[0-9]")
            2 ->
                pattern = Pattern.compile("^[0-9a-zA-Z]+$")     //英数文字列のみ
            3 ->
                pattern = Pattern.compile("^[^0-9]+$")          //数字以外
        }

        return if(!str.isEmpty()){
            true
        } else pattern!!.matcher(str).find()
    }
}
