package com.medicalrecord.data.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.medicalrecord.data.Patient
import com.medicalrecord.data.repositories.PatientRepository

class PatientViewModel(application: Application) : AndroidViewModel(application) {

    private val patientRepository: PatientRepository = PatientRepository(application)
    internal val allPatients: LiveData<List<Patient>>

    init {
        allPatients = patientRepository.getAllPatients()
    }

    fun insert(patient: Patient) {
        patientRepository.insert(patient)
    }
}