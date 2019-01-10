package com.example.g015c1153.aid

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_sign_up_form.*
import java.util.*

class SignUpFormFragment : Fragment() {

    private val valueResponse = ValueResponse()
    private val matcher = Matcher()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val userAdapter = moshi.adapter(User::class.java)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var strPW: String              //入力PW格納用
        var strPWRe: String            //入力PW再入力格納用
        var passMatch = false       //PWとPW再入力の一致判定用
        val userData = User()

        editPWRe.setOnFocusChangeListener { _, hasFocus ->
            //PW再入力のフォームからフォーカスが外れた時に発動
            if (!hasFocus) {
                strPW = editPW.text.toString()
                strPWRe = editPWRe.text.toString()
                passMatch = passMatcher(strPW,strPWRe)
            }
        }

        //不安要素。カレンダー表示。
        view!!.findViewById<EditText>(R.id.editBirthDay).setOnClickListener {
            showDatePickerDialog()
        }

        //確認ボタンが押された時の処理
        view!!.findViewById<Button>(R.id.buttonNextCheck).setOnClickListener {

            userData.lastName = editLastName.text.toString()   //姓の格納用
            userData.firstName = editFirstName.text.toString()  //名の格納用
            userData.birthDay = editBirthDay.text.toString()    //生年月日の格納用

            val flag = arrayOf(true, true, true, true, true)    //各項目が正しく入力されているかの判定用
            var lastFlag = true                                 //画面遷移させるかの判断用
            strPW = editPW.text.toString()
            strPWRe = editPWRe.text.toString()

            if (passMatcher(strPW,strPWRe)){
                userData.password = editPW.text.toString()
            }

            if (userData.password != "" || userData.lastName != "" || userData.firstName != "") {   //空文字判定

                val password = matcher.patternChecker(strPW, 2)
                val fn = matcher.patternChecker(userData.lastName, 2)
                val sn = matcher.patternChecker(userData.lastName, 3)

                val id = RadioGroupGender.checkedRadioButtonId     //チェックされたラジオボタンのIDを取得
                val radioButton = view!!.findViewById<View>(id) as RadioButton     //↑のIDをもとにRadioButtonのインスタンス化
                userData.gender = radioButton.text.toString()                    //ラジオボタンのvalueを取得

                //以下文字列チェックとエラー文の表示
                //パスワード
                if (!passMatch || userData.password == "" || !password) {
                    flag[0] = false
                    textPW.setTextColor(Color.RED)
                    textPWRe.setTextColor(Color.RED)
                }
                //苗字
                if (userData.lastName == "" || !fn) {
                    flag[1] = false
                    if (userData.lastName == "") {
                        editLastName.error = valueResponse.errorNoValue
                    } else {
                        editLastName.error = valueResponse.errorNumber
                    }
                }
                //名前
                if (userData.firstName == "" || !sn) {
                    flag[2] = false
                    if (userData.firstName == "") {
                        editFirstName.error = valueResponse.errorNoValue
                    } else {
                        editFirstName.error = valueResponse.errorNumber
                    }
                }
                //誕生日
                if (userData.birthDay == "") {
                    flag[3] = false
                    editBirthDay.error = valueResponse.errorNoValue
                }
                //性別
                if (userData.gender == "") {
                    flag[4] = false
                    radioButton.error = valueResponse.errorNoValue
                }

                //各項目に間違いがあればフラグをlastFalseに
                for (i in flag) {
                    if (!i){
                        lastFlag = false
                    }
                }

                //正しく入力されていれば、画面遷移
                if (lastFlag) {
                    //前画面からのメールアドレス情報をuserクラスに格納
                    //前画面からmailAddressを受け取って、userDataに追加
//                        val mailIntent = intent
//                        userData.mailAddress = mailIntent.getStringExtra("mailAddress")

                    //Jsonに変換
                    val userJson = userAdapter.toJson(userData)

                    //ここから画面遷移

                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_sign_up_form,
                container, false)
    }

    private fun passMatcher(strPW:String, strPWRe: String):Boolean {

        return if (!strPW.isEmpty() && !strPWRe.isEmpty()
                && strPW != strPWRe && strPWRe.length >=8 && strPW.length <= 16) { //空文字チェック、PWとPW再入力の一致を判定
            true
        }else {
            //エラー文の代入
            editPW.error = valueResponse.errorPasswordMismatch
            editPW.error = valueResponse.errorPasswordMismatch
            false
        }
    }

    //生年月日入力時のカレンダー入力用メソッド
    //(未修整)カレンダー表示ではなくスピナー表示にしたい
    private fun showDatePickerDialog() {
        //現在の日付を取得
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, _, monthOfYear, dayOfMonth ->
            //選択した日付を検出
            val date = year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth
            editBirthDay.setText(date)
        }, year, month, day)
        dpd.show()
    }

    companion object {
        fun newInstance(count: Int): SignUpFormFragment {
            // Fragemnt01 インスタンス生成
            val signUpFormFragment = SignUpFormFragment()

            // Bundle にパラメータを設定
            val args = Bundle()
            args.putInt("Counter", count)
            SendFragment.setArguments(args)

            return signUpFormFragment
        }
    }
}