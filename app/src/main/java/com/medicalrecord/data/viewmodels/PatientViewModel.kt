package com.medicalrecord.data.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.medicalrecord.data.Patient
import com.medicalrecord.data.repositories.PatientRepository

class PatientViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PatientRepository = PatientRepository(application)
    internal val all: LiveData<List<Patient>>

    init {
        all = repository.getAllPatients()
    }

    fun insert(patient: Patient) {
        repository.insert(patient)
    }
}