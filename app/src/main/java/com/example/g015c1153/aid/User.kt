package com.example.g015c1153.aid

import android.graphics.Bitmap

//新規登録用
data class User(
        var id: String = "",
        var firstName: String = "",
        var secondName: String = "",
        var birthDay: String = "",
        var gender: String = "",
        var mailAddress: String = "",
        var password: String = ""
)

//ログイン用
data class LoginData(
        var mailAddress: String = "",
        var password: String = ""
)

//TOP画面のチーム情報カード用
data class CardData(
        val cardImage: Bitmap,
        val cardTitle: String,
        val cardBody: String
)