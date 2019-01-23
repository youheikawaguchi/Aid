package com.example.g015c1153.aid

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.user_card_view.view.*

class UCRVHolder(view: View) : RecyclerView.ViewHolder(view) {

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemUCDate: TextView = view.date
    val itemUCTeamTitle: TextView = view.teamTitle
    val itemUCTitle: TextView = view.title
    val itemUCOverView: TextView = view.overview

    init {

    }
}