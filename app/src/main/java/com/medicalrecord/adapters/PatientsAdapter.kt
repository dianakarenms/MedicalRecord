package com.medicalrecord.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicalrecord.calcprenatal.R
import com.medicalrecord.data.PatientData
import kotlinx.android.synthetic.main.item_patient.view.*

class PatientsAdapter(var items: List<PatientData>, val listener: (PatientData) -> Unit): RecyclerView.Adapter<PatientsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(items[position], listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: PatientData, listener: (PatientData) -> Unit) = with(itemView) {
            itemPatientNameTxt.text = item.name
            itemPatientDateTxt.text = item.date
            setOnClickListener { listener(item) }
        }
    }
}