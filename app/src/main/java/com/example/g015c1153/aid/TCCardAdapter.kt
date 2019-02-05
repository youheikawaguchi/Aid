package com.example.g015c1153.aid

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class TCCardAdapter(private val context: Context, private val itemClickListener: TCRVHolder.ItemClickListener, private val itemList: ArrayList<TeamCalendarCard>) : RecyclerView.Adapter<TCRVHolder>() {

    private var mRecyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TCRVHolder {
        val layoutInflater = LayoutInflater.from(context)

        val mView = layoutInflater.inflate(R.layout.user_card_view, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }
        return TCRVHolder(mView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: TCRVHolder, position: Int) {
        holder.let {
            it.itemTCDate.text = itemList[position].cardStartDate
            it.itemTCTitle.text = itemList[position].cardTitle
            it.itemTCOverView.text = itemList[position].cardBody
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