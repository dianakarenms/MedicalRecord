package com.medicalrecord.data.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.MedicalRecordDataBase
import com.medicalrecord.data.Solution
import com.medicalrecord.data.repositories.CalculationRepository
import com.medicalrecord.data.repositories.SolutionsRepository

class CalculationViewModel(application: Application, patientId: Int) : AndroidViewModel(application) {

    private val calculationRepository: CalculationRepository = CalculationRepository(application)
    private val solutionsRepository: SolutionsRepository = SolutionsRepository(application)
    internal val all: LiveData<List<Calculation>>

    val medicalRecordDataBase = MedicalRecordDataBase.getInstance(application)

    init {
        all = calculationRepository.getCalculationsByPatientId(patientId)
    }

    fun insert(calculation: Calculation, solution: Solution) {
        calculationRepository.insert(calculation, solution)
    }

    fun insertSolution(solution: Solution) {
        solutionsRepository.insert(solution)

        /*val dao = medicalRecordDataBase?.solutionsDao()!!
        dao.insertCalculation(solution)*/
    }

    fun getAllSolutions() : LiveData<List<Solution>> {
        return solutionsRepository.getAllSolutions()
    }
}