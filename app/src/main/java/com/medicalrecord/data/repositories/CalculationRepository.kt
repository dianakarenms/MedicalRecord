package com.medicalrecord.data.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.CalculationsDao
import com.medicalrecord.data.MedicalRecordDataBase
import com.medicalrecord.data.Solution
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

    fun getCalculationsByPatientId(id: Int): LiveData<List<Calculation>> {
        return calculationsDao.getCalculationsByPatientId(id)
    }

    fun insert(calculation: Calculation, solution: Solution) {
       // var calculationValues: CalculationValues =
         //       CalculationValues(calculation, solution)

        //InsertAsyncTask(calculationsDao).execute(calculationValues)
        Observable.fromCallable { calculationsDao.insertCalculationWithValues(calculation, solution) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("calcRepo", "inserted calculation ${it.toString()}")
                }
        //InsertAsyncTask(calculationsDao).execute(calculation)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: CalculationsDao) : AsyncTask<Calculation, Void, Void>() {

        override fun doInBackground(vararg params: Calculation): Void? {
            mAsyncTaskDao.insertCalculation(params[0])
            return null
        }
    }
    /*private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: CalculationsDao) : AsyncTask<CalculationValues, Void, Void>() {

        override fun doInBackground(vararg params: CalculationValues): Void? {
            mAsyncTaskDao.insertCalculationWithValues(params[0].calculation, params[0].solution)
            return null
        }
    }*/

}