package com.medicalrecord.data.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import com.medicalrecord.calcprenatal.RefValue
import com.medicalrecord.data.*
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
        //CalculationsAsyncTask(calculationsDao).execute(id)
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