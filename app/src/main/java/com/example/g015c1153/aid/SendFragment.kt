package com.example.g015c1153.aid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class SendFragment : Fragment(){


    private var cnt = 0


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_send,
                container, false)

        val button = view!!.findViewById<Button>(R.id.button)
        //button.setOnClickListener()

    }

    companion object {
        fun newInstance(count: Int): SendFragment {
            // Fragemnt01 インスタンス生成
            val sendFragment = SendFragment()

            // Bundle にパラメータを設定
            val args = Bundle()
            args.putInt("Counter", count)
            sendFragment.arguments = args

            return sendFragment

            val count = args.getInt("Counter")
        }

        fun setArguments(args: Bundle) {

        }
    }

}
