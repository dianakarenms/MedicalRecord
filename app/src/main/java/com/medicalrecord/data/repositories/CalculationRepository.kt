package com.medicalrecord.data.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.util.Log
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.CalculationsDao
import com.medicalrecord.data.MedicalRecordDataBase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CalculationRepository(application: Application) {

    private val calculationsDao: CalculationsDao
    private val listLiveData: LiveData<List<Calculation>>

    init {
        val medicalRecordDataBase = MedicalRecordDataBase.getInstance(application)
        calculationsDao = medicalRecordDataBase?.calculationsDao()!!
        listLiveData = calculationsDao.getAll()
    }

    fun getAllCalculations(): LiveData<List<Calculation>> {
        return listLiveData
    }

    fun getCalculationsByPatientId(id: Long): LiveData<List<Calculation>> {
        return calculationsDao.getCalculationsByPatientId(id)
    }

    fun insert(calculation: Calculation) {
        Observable.fromCallable { calculationsDao.insertCalculationWithValues(calculation) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("calcRepo", "inserted calculation ${it.toString()}")
                }
    }
}