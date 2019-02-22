package com.example.g015c1153.aid

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telecom.Call
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_member_add.*

class MemberAdd : AppCompatActivity() {

//    private lateinit var mRealm: Realm
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val userAdapter = moshi.adapter(User::class.java)
    private val memberAdapter = moshi.adapter(Member::class.java)
    var user = User()
    private lateinit var pref : SharedPreferences
    //onCreate()のmemberMailSearchリスナー内。Userのメールアドレスを渡して、ユーザー情報を取得
    //onCreate()のmemberAddButtonリスナー内。メンバー情報を渡して、データを追加する。送信のみ。
    private val mailSearchURL = ValueResponse().serverIp + "/mMemberSearch"     //ユーザーをメンバーで探す用パス。
    private val memberAddURL = ValueResponse().serverIp + "/mMemberAdd"        //メンバー追加用パス。


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_add)

        //Realmのセットアップ
//        val realmConfig = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
//        mRealm = Realm.getInstance(realmConfig)
        var frag = false

        memberSearchButton.setOnClickListener {       //ユーザー検索ボタンが押されたとき

            user.mailAddress = memberMailSearch.text.toString()

            var json = userAdapter.toJson(user)
            json = CallOkHttp().postRun(mailSearchURL, json)      //メールアドレスを渡す
            val fromJson = userAdapter.fromJson(json)
            if(fromJson != null){
                user = fromJson
                val userName: String = user.firstName + user.lastName
                textUserName.text = userName
                frag = true
            }else{
                memberMailSearch.error = ValueResponse().errorNoUser
            }
        }

        memberAddButton.setOnClickListener {
            if(frag) {
                val mMemberNo = memberNo.text.toString()
                val mMemberPosition = memberPosition.text.toString()

                val memberData = Member()
                pref = getSharedPreferences("Aid_Session", Context.MODE_PRIVATE)
                memberData.teamid = pref.getString("TeamID", "Unknown")!!
                memberData.Userid = user.id
                memberData.number = mMemberNo
                memberData.position = mMemberPosition

                val toJson = memberAdapter.toJson(memberData)
                CallOkHttp().postRun(memberAddURL, toJson)

                //Sessionに登録されているチームIDを取得してくる。
//                val teamData = RealmDAO().sessionRead(memberData.Userid) //チームIDが取得できていない

                finish()
            }
        }
    }
}
