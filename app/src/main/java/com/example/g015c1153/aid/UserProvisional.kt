package com.example.g015c1153.aid

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class UserProvisional(
        @PrimaryKey open var Id: String = "",
        @Required open var MailAddress: String = "",
        @Required open var Password: String = ""
) : RealmObject()

open class UserSignUp(
        @PrimaryKey open var Id: String = "",
        @Required open var FirstName: String = "",
        @Required open var SecondName: String = "",
        @Required open var Gender: String = "",
        @Required open var Birthday: String = "",
        @Required open var MailAddress: String = "",
        @Required open var Password: String = ""

) : RealmObject()