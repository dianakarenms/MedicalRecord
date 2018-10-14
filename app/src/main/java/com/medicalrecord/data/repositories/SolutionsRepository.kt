package com.medicalrecord.data.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.medicalrecord.data.MedicalRecordDataBase
import com.medicalrecord.data.Solution
import com.medicalrecord.data.SolutionsDao

class SolutionsRepository(application: Application) {

    private val solutionsDao: SolutionsDao
    private val listLiveData: LiveData<List<Solution>>

    init {
        val medicalRecordDataBase = MedicalRecordDataBase.getInstance(application)
        solutionsDao = medicalRecordDataBase?.solutionsDao()!!
        listLiveData = solutionsDao.getAll()
    }

    fun getAllSolutions(): LiveData<List<Solution>> {
        return listLiveData
    }

    fun getSolutionById(id: Int): LiveData<Solution> {
        return solutionsDao.getSolutionById(id)
    }

    fun insert(solution: Solution) {
        InsertAsyncTask(solutionsDao).execute(solution)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: SolutionsDao) : AsyncTask<Solution, Void, Void>() {

        override fun doInBackground(vararg params: Solution): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

}