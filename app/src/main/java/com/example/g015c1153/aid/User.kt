package com.example.g015c1153.aid

import android.graphics.drawable.Drawable

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
        var password: String = "",
        var frag: Boolean = false
)

//TOP画面のチーム情報カード用
data class CardData(
        var cardTeamId: String,
        var cardImage: Drawable,
        var cardTitle: String,
        var cardBody: String
)

//Teamのデータ
data class TeamData(
        var TeamId: String = "",
        var teamName: String = "",
        var teamDetail: String = "",
        var teamLocal: String = ""
)

data class Session(
        var userId: String = "",
        var teamId: String = ""
)

data class Member(
        var id: String = "",
        var teamid: String = "", //foreign key
        var Userid: String ="",    //foreign key
        var number: String = "",
        var position: String = ""
)