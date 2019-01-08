package com.medicalrecord.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicalrecord.calcprenatal.R
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.RefValue
import com.medicalrecord.utils.Constants
import com.medicalrecord.utils.decimalsFormat
import kotlinx.android.synthetic.main.item_calculation.view.*
import kotlinx.android.synthetic.main.item_ref_value.view.*

class CalculationsAdapter(private val listener: (Calculation, Int) -> Unit): RecyclerView.Adapter<CalculationsAdapter.ValueViewHolder>() {
    private var items: List<Calculation> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_calculation, parent, false)
        return ValueViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    internal fun setCalculations(list: List<Calculation>) {
        this.items = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ValueViewHolder, position: Int) =
            holder.bind(items[position], position, listener)

    class ValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Calculation, position: Int, listener: (Calculation, Int) -> Unit) = with(itemView) {
            itemCalculationDateTxt.text = item.date
            itemCalculationWeightTxt.text = item.weight.toString()
            setOnClickListener { listener(item, position) }
        }
    }
}



