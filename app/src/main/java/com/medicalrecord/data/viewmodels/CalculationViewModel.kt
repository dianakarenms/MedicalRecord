package com.medicalrecord.data.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.repositories.CalculationRepository

class CalculationViewModel(application: Application, patientId: Int) : AndroidViewModel(application) {

    private val repository: CalculationRepository = CalculationRepository(application)
    internal val all: LiveData<List<Calculation>>

    init {
        all = repository.getCalculationsByPatientId(patientId)
    }

    fun insert(calculation: Calculation) {
        repository.insert(calculation)
    }
}