package com.example.g015c1153.aid

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


//カードビューに対する処理。現状俺もよくわかってない。
class CardAdapter(private val context: Context, private val itemClickListener: RecyclerViewHolder.ItemClickListener, private val itemList: List<String>) : RecyclerView.Adapter<RecyclerViewHolder>() {

    private var mRecyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.card_view_layout, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }
        return RecyclerViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.let {
            it.itemImageView.setImageResource(R.mipmap.ic_launcher)
            it.itemTextView.text = itemList[position]
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