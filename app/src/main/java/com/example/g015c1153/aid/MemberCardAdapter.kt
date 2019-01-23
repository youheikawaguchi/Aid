package com.example.g015c1153.aid

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class MemberCardAdapter(private val context: Context, private val itemClickListener: MemberRVHolder.ItemClickListener, private val itemList: ArrayList<MemberCard>) : RecyclerView.Adapter<MemberRVHolder>() {

    private var mRecyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberRVHolder {
        val layoutInflater = LayoutInflater.from(context)

        val mView = layoutInflater.inflate(R.layout.member_card_view, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }
        return MemberRVHolder(mView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MemberRVHolder, position: Int) {
        holder.let {
            it.itemLineNum.text = position.toString()
            it.itemMemberName.text = itemList[position].cardMemberName
            it.itemMemberNumber.text = itemList[position].cardMemberNumber
            it.itemMemberPosition.text = itemList[position].cardMemberPosition
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