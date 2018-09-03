package com.example.g015c1153.aid

import android.graphics.Bitmap

data class User(
        val id:String,
        val password : String,
        val firstName : String,
        val secondName : String,
        val BirthDay: String,
        val Sex : String
)

data class LoginData(
        val id:String,
        val password: String
)

data class CardData(
        val cardImage : Bitmap,
        val cardTitle : String,
        val cardBody : String
)