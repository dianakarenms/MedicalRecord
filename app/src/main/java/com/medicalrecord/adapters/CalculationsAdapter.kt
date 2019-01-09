package com.medicalrecord.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicalrecord.calcprenatal.R
import com.medicalrecord.data.Calculation
import kotlinx.android.synthetic.main.item_calculation.view.*

class CalculationsAdapter(private val listener: (Calculation) -> Unit): RecyclerView.Adapter<CalculationsAdapter.ValueViewHolder>() {
    private var items: List<Calculation> = listOf()
    var lastSelectedView: View? = null
    var lastSelectedPostion = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_calculation, parent, false)
        return ValueViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    internal fun setCalculations(list: List<Calculation>) {
        items = list

        // Restablish values to handle calculations list update
        lastSelectedView = null
        lastSelectedPostion = -1

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ValueViewHolder, position: Int) =
            holder.bind(items[position], listener)

    inner class ValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Calculation, listener: (Calculation) -> Unit) = with(itemView) {
            itemCalculationDateTxt.text = item.date
            itemCalculationWeightTxt.text = "${item.weight} Kg"

            // When data is first updated select first list item
            if(lastSelectedPostion == -1 && adapterPosition == 0) {
                lastSelectedView = this
                lastSelectedPostion = 0
            }

            // If the lastSelectedPosition is the one being drawn show the square
            if (adapterPosition == lastSelectedPostion) {
                itemCalculationParent.background = context.getDrawable(R.drawable.shape_rounded_square_border_gray_dark)
            } else {
                itemCalculationParent.setBackgroundResource(0)
            }

            setOnClickListener {
                // update views
                lastSelectedView?.itemCalculationParent?.setBackgroundResource(0) // remove square from previous position
                it.itemCalculationParent.background = context.getDrawable(R.drawable.shape_rounded_square_border_gray_dark) // add square to new selection

                // update selection
                lastSelectedView = it
                lastSelectedPostion = adapterPosition

                listener(item)
            }
        }
    }
}



