package com.medicalrecord.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicalrecord.calcprenatal.R
import com.medicalrecord.calcprenatal.Value
import com.medicalrecord.utils.Constants
import com.medicalrecord.utils.editable
import kotlinx.android.synthetic.main.item_ref_value.view.*

class ValuesAdapter(private var items: List<Value>, private val listener: (Value) -> Unit): RecyclerView.Adapter<ValuesAdapter.ValueViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_ref_value, parent, false)
        return ValueViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ValueViewHolder, position: Int) =
            holder.bind(items[position], listener)

    class ValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Value, listener: (Value) -> Unit) = with(itemView) {
            itemRefValueNameTxt.text = Constants.displayNames[item.name]!!
            itemRefValueValueTxt.text = item.value.editable
            setOnClickListener { listener(item) }
        }
    }
}

