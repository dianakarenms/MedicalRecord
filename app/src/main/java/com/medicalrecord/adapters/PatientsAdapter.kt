package com.medicalrecord.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicalrecord.calcprenatal.R
import com.medicalrecord.data.Patient
import kotlinx.android.synthetic.main.item_patient.view.*

class PatientsAdapter(private val listener: (Patient) -> Unit): RecyclerView.Adapter<PatientsAdapter.PatientViewHolder>() {
    private var items: List<Patient> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    internal fun setPatients(patientsList: List<Patient>) {
        this.items = patientsList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) =
            holder.bind(items[position], listener)

    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Patient, listener: (Patient) -> Unit) = with(itemView) {
            itemPatientNameTxt.text = item.name
            itemPatientDateTxt.text = item.date
            setOnClickListener { listener(item) }
        }
    }
}