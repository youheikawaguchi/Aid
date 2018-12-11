package com.example.g015c1153.aid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_member_add.*

class MemberAdd : AppCompatActivity() {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val memberAddAdapter = moshi.adapter(User::class.java)!!
    var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_add)

        memberMailSearch.setOnClickListener {

            user.mailAddress = memberMailSearch.text.toString()

            var json = memberAddAdapter.toJson(user)
            val url = ValueResponse().serverIp + ""

            json = CallOkHttp().postRun(url, json)
            val fromJson = memberAddAdapter.fromJson(json)
            if(fromJson != null){
                user = fromJson
                val userName: String = user.firstName + user.secondName
                textUserName.text = userName
            }else{
                memberMailSearch.error = ValueResponse().errorNoUser
            }
        }

        memberAddButton.setOnClickListener {
            val mMemberNo = memberNo.text.toString()
            val mMemberPosition = memberPosition.text.toString()
        }

    }
}
