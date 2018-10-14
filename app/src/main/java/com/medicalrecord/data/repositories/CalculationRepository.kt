package com.medicalrecord.data.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.CalculationsDao
import com.medicalrecord.data.MedicalRecordDataBase

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

    fun insert(calculation: Calculation) {
        InsertAsyncTask(calculationsDao).execute(calculation)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: CalculationsDao) : AsyncTask<Calculation, Void, Void>() {

        override fun doInBackground(vararg params: Calculation): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

}