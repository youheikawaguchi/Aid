package com.example.g015c1153.aid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class MemberList : AppCompatActivity() {

    private var mDataList: ArrayList<CardData> = ArrayList()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val type = Types.newParameterizedType(List::class.java, User::class.java)
    private val userListAdapter: JsonAdapter<List<User>> = moshi.adapter(type)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)


    }
}
