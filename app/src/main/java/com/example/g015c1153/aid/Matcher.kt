package com.example.g015c1153.aid

import android.support.v7.app.AppCompatActivity

import java.util.regex.Pattern

class Matcher : AppCompatActivity() {

    fun address(adress: String): Boolean {
        val addPattern = Pattern.compile("^[0-9]+$")

        return if (adress.isEmpty()) {
            true
        } else addPattern.matcher(adress).find()
    }

    fun domain(domain: String): Boolean {
        val doPattern = Pattern.compile("[0-9]")

        return if (domain.isEmpty()) {
            true
        } else doPattern.matcher(domain).find()
    }
}
