package com.example.g015c1153.aid

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class UCCardAdapter(private val context: Context, private val itemClickListener: UCRVHolder.ItemClickListener, private val itemList: ArrayList<UserCalendarCard>) : RecyclerView.Adapter<UCRVHolder>() {

    private var mRecyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UCRVHolder {
        val layoutInflater = LayoutInflater.from(context)

        val mView = layoutInflater.inflate(R.layout.user_card_view, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }
        return UCRVHolder(mView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: UCRVHolder, position: Int) {
        holder.let {
            it.itemUCDate.text = itemList[position].cardDate
            it.itemUCTeamTitle.text = itemList[position].cardTitle
            it.itemUCTitle.text = itemList[position].cardTitle
            it.itemUCOverView.text = itemList[position].cardBody
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

}