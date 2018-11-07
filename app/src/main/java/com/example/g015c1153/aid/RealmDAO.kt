package com.example.g015c1153.aid

import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*

class RealmDAO {

    fun mailRealmAdd(mailStr: String) {
        lateinit var mRealm: Realm

        //Realmのセットアップ
        val realmConfig = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        mRealm = Realm.getInstance(realmConfig)

        mRealm.executeTransaction {

            //8-16桁の英数字のパスワードを生成
            val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
            val maxNum = arrayListOf(8, 9, 10, 11, 12, 13, 14, 15, 16)
            val index = Random().nextInt(9)
            var str = ""
            while (str.length <= maxNum[index]) {
                str += letters[Random().nextInt(letters.length)]
            }

            //IDを生成
            // 初期化
            var nextUserId = 1
            // userIdの最大値を取得
            val maxUserId = mRealm.where(UserProvisional::class.java).max("Id")
            // 1度もデータが作成されていない場合はNULLが返ってくるため、NULLチェックをする
            if (maxUserId != null) {
                nextUserId = maxUserId.toInt() + 1
            }

            //Realmオブジェクトの生成
            val user = mRealm.createObject(UserProvisional::class.java,nextUserId)
            user.MailAddress = mailStr
            user.Password = str
            //Realmの登録
            mRealm.copyToRealm(user)
        }
        Log.i("保存された内容：", mRealm.where(UserProvisional::class.java).findAll().toString())
        mRealm.close()
    }

    //Realmにメールアドレスを保存用のメソッド
    fun signUpRealmAdd(user: User) {

        lateinit var mRealm: Realm

        //Realmのセットアップ
        val realmConfig = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        mRealm = Realm.getInstance(realmConfig)

        //IDを生成
        // 初期化
        var nextUserId = 1
        // userIdの最大値を取得
        val maxUserId = mRealm.where(UserSignUp::class.java).max("Id")
        // 1度もデータが作成されていない場合はNULLが返ってくるため、NULLチェックをする
        if (maxUserId != null) {
            nextUserId = maxUserId.toInt() + 1
        }

        mRealm.executeTransaction {
            //Realmオブジェクトの生成
            val signUpUser = mRealm.createObject(UserSignUp::class.java,nextUserId)
            signUpUser.Password = user.password
            signUpUser.FirstName = user.firstName
            signUpUser.SecondName = user.secondName
            signUpUser.Birthday = user.birthDay
            signUpUser.Gender = user.gender
            //Realmの登録
            mRealm.copyToRealm(signUpUser)
        }
        Log.i("保存された内容：", mRealm.where(UserSignUp::class.java).findAll().toString())
        mRealm.close()
    }

    fun loginRealm(loginData: LoginData): Int {
        lateinit var mRealm: Realm

        //Realmのセットアップ
        val realmConfig = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        mRealm = Realm.getInstance(realmConfig)

        if (mRealm.where(UserSignUp::class.java)
                        .equalTo("MailAddress", loginData.mailAddress)
                        .equalTo("Password", loginData.password).findAll() != null) {
            mRealm.close()
            return 1
        } else if (mRealm.where(UserProvisional::class.java)
                        .equalTo("MailAddress", loginData.mailAddress)
                        .equalTo("Password", loginData.password).findAll() != null) {
            mRealm.close()
            return 2
        }
        mRealm.close()
        return 0
    }

    fun teamAddRealm(teamDataAdd: TeamData): Int {
        lateinit var mRealm: Realm

        //Realmのセットアップ
        val realmConfig = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        mRealm = Realm.getInstance(realmConfig)

        //IDを生成
        // 初期化
        var nextTeamId = 1
        // userIdの最大値を取得
        val maxTeamId = mRealm.where(Team::class.java).max("Id")
        // 1度もデータが作成されていない場合はNULLが返ってくるため、NULLチェックをする
        if (maxTeamId != null) {
            nextTeamId = maxTeamId.toInt() + 1
        }

        mRealm.executeTransaction {
            //チームデータをRealmに保存
            val teamAdd = mRealm.createObject(Team::class.java, nextTeamId)
            teamAdd.TeamName = teamDataAdd.teamName
            teamAdd.TeamDetail = teamDataAdd.teamDetail
            teamAdd.TeamLocal = teamDataAdd.teamLocal
            mRealm.copyToRealm(teamAdd)
        }

        Log.i("保存された内容：", mRealm.where(Team::class.java).findAll().toString())
        mRealm.close()

        return nextTeamId
    }

    fun teamReadRealm(teamId : Int): TeamData {
        lateinit var mRealm: Realm
        val teamDataRead = TeamData()

        //Realmのセットアップ
        val realmConfig = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        mRealm = Realm.getInstance(realmConfig)

        val teamDataRealm = mRealm.where(Team::class.java).equalTo("Id", teamId).findAll()
        if(teamDataRealm != null){
            teamDataRead.TeamId = teamDataRealm[0]!!.Id.toString()
            teamDataRead.teamName = teamDataRealm[0]!!.TeamName
            teamDataRead.teamDetail = teamDataRealm[0]!!.TeamDetail
            teamDataRead.teamLocal = teamDataRealm[0]!!.TeamLocal
        }
        mRealm.close()
        return teamDataRead
    }
}