package com.example.g015c1153.aid

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.card_view_layout.view.*
import kotlinx.android.synthetic.main.user_card_view.view.*

class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemImageView: ImageView = view.cardImage
    val itemTextView: TextView = view.cardTitle
    val itemDetailView: TextView = view.cardBody

    init {

    }

}