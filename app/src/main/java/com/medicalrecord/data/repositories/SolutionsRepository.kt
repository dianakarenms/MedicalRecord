package com.medicalrecord.data.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import com.medicalrecord.data.MedicalRecordDataBase
import com.medicalrecord.data.Solution
import com.medicalrecord.data.SolutionsDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SolutionsRepository(application: Application) {

    private val dao: SolutionsDao
    private val listLiveData: LiveData<List<Solution>>

    init {
        val medicalRecordDataBase = MedicalRecordDataBase.getInstance(application)
        dao = medicalRecordDataBase?.solutionsDao()!!
        listLiveData = dao.getAll()
    }

    fun getAllSolutions(): LiveData<List<Solution>> {
        return listLiveData
    }

    fun getSolutionById(id: Int): LiveData<Solution> {
        return dao.getSolutionById(id)
    }

    fun insert(solution: Solution) {
        //InsertAsyncTask(dao).execute(solution)
        Observable.fromCallable { dao.insert(solution) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("solRepo", "inserted solution ${it.toString()}")
                }
    }

    fun getLastInsertedId() : Long {
        return dao.getLastInsertedId()
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: SolutionsDao) : AsyncTask<Solution, Void, Void>() {

        override fun doInBackground(vararg params: Solution): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

}