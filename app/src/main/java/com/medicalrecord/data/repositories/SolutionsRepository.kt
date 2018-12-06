package com.medicalrecord.data.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.util.Log
import com.medicalrecord.data.CalculationsDao
import com.medicalrecord.data.MedicalRecordDataBase
import com.medicalrecord.data.Solution
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SolutionsRepository(application: Application) {

    private val dao: CalculationsDao
    private val listLiveData: LiveData<List<Solution>>

    init {
        val medicalRecordDataBase = MedicalRecordDataBase.getInstance(application)
        dao = medicalRecordDataBase?.calculationsDao()!!
        listLiveData = dao.getAllSolution()
    }

    fun getAllSolutions(): LiveData<List<Solution>> {
        return listLiveData
    }

    fun getSolutionById(id: Long): Solution {
        return dao.getSolutionById(id)!!
    }

    fun insert(solution: Solution) {
        Observable.fromCallable { dao.insertSolution(solution) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("solRepo", "inserted solution ${it.toString()}")
                }
    }
}