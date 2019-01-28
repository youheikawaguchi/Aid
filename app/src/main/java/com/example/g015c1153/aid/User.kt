package com.example.g015c1153.aid

import android.graphics.drawable.Drawable

//新規登録用
data class User(
        var id: String = "",
        var lastName: String = "",
        var firstName: String = "",
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
        var cardUserId: String,
        var cardImage: Drawable,
        var cardTitle: String,
        var cardBody: String
)

//メンバー表用のカードビュー
data class MemberCard(
        var cardId: String,
        var cardMemberName: String,
        var cardMemberNumber: String,
        var cardMemberPosition: String
)

//チームカレンダー用のカードビュー
data class TeamCalendarCard(
        var cardId: String,
        var cardDate: String,
        var cardTitle: String,
        var cardBody: String,
        var resultID: String
)

//ユーザーカレンダー用のカードビュー
data class UserCalendarCard(
        var cardId: String,
        var cardDate: String,
        var cardTeamName: String,
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

//内部処理：値保持用
data class Session(
        var userId: String = "",
        var teamId: String = ""
)

//メンバー表登録(01/26:追加)取得用
data class Member(
        var teamid: String = "",
        var Userid: String ="",
        var number: String = "",    // 01/26:追加
        var position: String = ""   // 01/26:追加
)

//練習登録・表示用
data class Practice(
        var startDate: String = "",
        var endDate: String = "",
        var place: String = "",
        var memo: String = ""
)

//試合登録・表示用
data class Game(
        var startDate: String = "",
        var endDate: String = "",
        var place: String = "",
        var memo: String = "",
        var otherTeam: String = ""
)

data class GameData(
        var id: String = "",
        var teamID: String = "",
        var planID: String = "",
        var myTeam: String = "",
        var otherTeam: String = "",
        var myPoint: String = "",
        var otherPoint: String = "",
        var myFirstHalf: String = "",
        var otherFirstHalf: String = "",
        var myLatterHalf: String = "",
        var otherLatterHalf: String = ""
)