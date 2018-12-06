package com.medicalrecord.data.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.medicalrecord.data.AdditionalInfo
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.DoctorReference
import com.medicalrecord.data.Solution
import com.medicalrecord.data.repositories.CalculationRepository

class CalculationViewModel(application: Application, patientId: Long) : AndroidViewModel(application) {

    private val calculationRepository: CalculationRepository = CalculationRepository(application)
    //private val solutionsRepository: SolutionsRepository = SolutionsRepository(application)
    internal val all: LiveData<List<Calculation>>

    //val medicalRecordDataBase = MedicalRecordDataBase.getInstance(application)

    init {
        all = calculationRepository.getCalculationsByPatientId(patientId)
    }

    fun insert(calculation: Calculation, solution: Solution, additionalInfo: AdditionalInfo, doctorReference: DoctorReference) {
        calculationRepository.insert(calculation, solution, additionalInfo, doctorReference)
    }

    fun getCalculationsByPatientId(id: Long) : LiveData<List<Calculation>>{
        return calculationRepository.getCalculationsByPatientId(id)
    }

    /*fun getAllSolutions() : LiveData<List<Solution>> {
        return solutionsRepository.getAllSolutions()
    }*/
}