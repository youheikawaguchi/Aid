package com.example.g015c1153.aid

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class UserProvisional(
        @PrimaryKey open var Id: Int = 0,
        @Required open var MailAddress: String = "",
        @Required open var Password: String = ""
) : RealmObject()

open class UserSignUp(
        @PrimaryKey open var Id: Int = 0,
        @Required open var FirstName: String = "",
        @Required open var SecondName: String = "",
        @Required open var Gender: String = "",
        @Required open var Birthday: String = "",
        @Required open var MailAddress: String = "",
        @Required open var Password: String = ""
) : RealmObject()

open class Team(
        @PrimaryKey open var Id: Int = 0,
        @Required open var TeamName: String = "",
        @Required open var TeamDetail: String = "",
        @Required open var TeamLocal: String = ""
) : RealmObject()

open class SessionData(
        @PrimaryKey open var Id: Int = 1,
        @Required open var UserId: String = "",
        @Required open var TeamId: String = ""
) : RealmObject()