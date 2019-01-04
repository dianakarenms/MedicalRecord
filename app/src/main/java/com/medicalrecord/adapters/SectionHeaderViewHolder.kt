package com.medicalrecord.adapters

import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import com.medicalrecord.calcprenatal.R


class SectionHeaderViewHolder(itemView: View, private val callback: HeaderViewHolderCallback) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    internal var sectionTitle: TextView = itemView.findViewById(R.id.itemRefValueHeaderCollapseBtn)

    internal var arrowUp: Drawable? = null
    internal var arrowDown: Drawable? = null

    init {

        arrowUp = ContextCompat.getDrawable(itemView.context, android.R.drawable.arrow_up_float)
        arrowDown = ContextCompat.getDrawable(itemView.context, android.R.drawable.arrow_down_float)

        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val position = adapterPosition
        callback.onHeaderClick(position)
        if (callback.isExpanded(position)) {
            sectionTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowUp, null)
        } else {
            sectionTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDown, null)
        }
    }

    interface HeaderViewHolderCallback {
        fun onHeaderClick(position: Int)

        fun isExpanded(position: Int): Boolean
    }

}