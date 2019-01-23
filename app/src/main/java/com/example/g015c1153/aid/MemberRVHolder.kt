package com.example.g015c1153.aid

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.member_card_view.view.*

class MemberRVHolder(view: View) : RecyclerView.ViewHolder(view) {

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemLineNum: TextView = view.line
    val itemMemberName: TextView = view.name
    val itemMemberPosition :TextView = view.position
    val itemMemberNumber: TextView = view.number

    init {

    }
}