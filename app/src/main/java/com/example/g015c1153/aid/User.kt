package com.example.g015c1153.aid

import android.graphics.Bitmap

//新規登録用
data class User(
        val id: String,
        val password: String,
        val firstName: String,
        val secondName: String,
        val BirthDay: String,
        val Sex: String
)

//ログイン用
data class LoginData(
        val id: String,
        val password: String
)

//TOP画面のチーム情報カード用
data class CardData(
        val cardImage: Bitmap,
        val cardTitle: String,
        val cardBody: String
)