package com.medicalrecord.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicalrecord.calcprenatal.R
import com.medicalrecord.calcprenatal.RefValue
import com.medicalrecord.utils.Constants
import kotlinx.android.synthetic.main.item_ref_value.view.*

class ValuesAdapter(private val listener: (RefValue, Int) -> Unit): RecyclerView.Adapter<ValuesAdapter.ValueViewHolder>() {
    private var items: List<RefValue> = listOf()

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
            itemRefValueValueTxt.text = "%.2f".format(item.value)
            setOnClickListener { listener(item, position) }
        }
    }
}

