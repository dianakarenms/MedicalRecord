package com.medicalrecord.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.medicalrecord.calcprenatal.R


class SectionHeaderViewHolder(itemView: View, private val callback: HeaderViewHolderCallback) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    internal var sectionTitle: TextView = itemView.findViewById(R.id.itemRefValueHeaderTitleLabel)
    internal var sectionButton: TextView = itemView.findViewById(R.id.itemRefValueHeaderCollapseBtn)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val position = adapterPosition
        callback.onHeaderClick(position)
        if (callback.isExpanded(position)) {
            sectionButton.text = "Colapsar"
        } else {
            sectionButton.text = "Expandir"
        }
    }

    interface HeaderViewHolderCallback {
        fun onHeaderClick(position: Int)

        fun isExpanded(position: Int): Boolean
    }

}