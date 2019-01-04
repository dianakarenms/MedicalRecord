package com.medicalrecord.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.accordion_library.AccordionAdapter
import beepbeep.accordion_library.AccordionView
import com.medicalrecord.calcprenatal.R
import com.medicalrecord.data.RefValue
import com.medicalrecord.utils.Constants
import com.medicalrecord.utils.decimalsFormat
import kotlinx.android.synthetic.main.item_ref_value.view.*
import kotlinx.android.synthetic.main.item_ref_value_header.view.*

class CalculationsAccordionAdapter(val items: List<RefValue>): AccordionAdapter {
    override fun onCreateViewHolderForTitle(parent: ViewGroup): AccordionView.ViewHolder {
        return TitleViewHolder.create(parent)
    }

    override fun onCreateViewHolderForContent(parent: ViewGroup): AccordionView.ViewHolder {
        return ContentViewHolder.create(parent)
    }

    override fun onBindViewForTitle(viewHolder: AccordionView.ViewHolder, position: Int, arrowDirection: AccordionAdapter.ArrowDirection) {
        if(position == 0) {
            //val dataModel = items[position]
            (viewHolder as TitleViewHolder).itemView.apply {
                itemRefValueHeaderTitleLabel.text = "Solución:"
                when (arrowDirection) {
                    AccordionAdapter.ArrowDirection.UP -> itemRefValueHeaderCollapse1Btn.text = "Collapsar"
                    AccordionAdapter.ArrowDirection.DOWN -> itemRefValueHeaderCollapse1Btn.text = "Expandir"
                    AccordionAdapter.ArrowDirection.NONE -> itemRefValueHeaderCollapse1Btn.text = ""
                }
            }
        }
    }

    override fun onBindViewForContent(viewHolder: AccordionView.ViewHolder, position: Int) {
        val item = items[position]
        (viewHolder as ContentViewHolder).itemView.apply {
            val name = Constants.displayNames[item.name]
            itemRefValueNameTxt.text = name
            itemRefValueValueTxt.text = item.value.decimalsFormat(2)
        }
    }

    override fun getItemCount() = items.size
}

class TitleViewHolder(itemView: View) : AccordionView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): TitleViewHolder {
            return TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ref_value_header, parent, false))
        }
    }
}

class ContentViewHolder(itemView: View) : AccordionView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): ContentViewHolder {
            return ContentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ref_value, parent, false))
        }
    }
}
/*private var items: List<RefValue> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_ref_value, parent, false)
        return ValueViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    internal fun setRefValues(list: List<RefValue>) {
        this.items = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ValueViewHolder, position: Int) =
            holder.bind(items[position], position, listener)

    class ValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RefValue, position: Int, listener: (RefValue, Int) -> Unit) = with(itemView) {
            val name = Constants.displayNames[item.name]
            itemRefValueNameTxt.text = name
            itemRefValueValueTxt.text = item.value.decimalsFormat(2)
            setOnClickListener { listener(item, position) }
        }
    }
}*/



