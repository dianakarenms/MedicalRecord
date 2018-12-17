package com.medicalrecord.data.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.medicalrecord.data.MedicalRecordDataBase
import com.medicalrecord.data.Patient
import com.medicalrecord.data.PatientsDao

class PatientRepository(application: Application) {

    private val patientsDao: PatientsDao
    private val listLiveData: LiveData<List<Patient>>

    init {
        val medicalRecordDataBase = MedicalRecordDataBase.getInstance(application)
        patientsDao = medicalRecordDataBase?.patientDao()!!
        listLiveData = patientsDao.getAll()
    }

    fun getAllPatients(): LiveData<List<Patient>> {
        return listLiveData
    }

    fun insert(patient: Patient) {
        InsertAsyncTask(patientsDao).execute(patient)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: PatientsDao) : AsyncTask<Patient, Void, Void>() {
        override fun doInBackground(vararg params: Patient): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

}