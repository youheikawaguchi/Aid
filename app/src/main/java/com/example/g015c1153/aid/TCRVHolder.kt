package com.example.g015c1153.aid

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.team_card_view.view.*

class TCRVHolder (view: View) : RecyclerView.ViewHolder(view) {

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemTCDate: TextView = view.date
    val itemTCTitle: TextView = view.title
    val itemTCOverView: TextView = view.overview

    init {
    }
}