package com.medicalrecord.data.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.repositories.CalculationRepository

class CalculationViewModel(application: Application, patientId: Long) : AndroidViewModel(application) {

    private val calculationRepository: CalculationRepository = CalculationRepository(application)
    internal val all: LiveData<List<Calculation>>

    init {
        all = calculationRepository.getCalculationsByPatientId(patientId)
    }

    fun insert(calculation: Calculation) {
        calculationRepository.insert(calculation)
    }

    fun getCalculationsByPatientId(id: Long) : LiveData<List<Calculation>>{
        return calculationRepository.getCalculationsByPatientId(id)
    }
}