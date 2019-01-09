package com.medicalrecord.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.os.AsyncTask

@Database(entities =
[
    Patient::class,
    Calculation::class
], version = 1)
@TypeConverters(Converters::class)
abstract class MedicalRecordDataBase : RoomDatabase() {

    abstract fun patientDao(): PatientsDao
    abstract fun calculationsDao(): CalculationsDao

    companion object {
        private var INSTANCE: MedicalRecordDataBase? = null

        fun getInstance(context: Context): MedicalRecordDataBase? {
            if (INSTANCE == null) {
                synchronized(MedicalRecordDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            MedicalRecordDataBase::class.java, "medicalrecord.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        fun clearDb(listener: OnDeleteCompleted) {
            if (INSTANCE != null) {
                DeleteAllAsyncTask(INSTANCE!!, listener).execute()
            }
        }
    }

    private class DeleteAllAsyncTask internal constructor(private val instance: MedicalRecordDataBase, private val listener: OnDeleteCompleted) : AsyncTask<Void, Void, Void>() {
        private lateinit var patientsDao: PatientsDao
        private lateinit var calculationsDao: CalculationsDao

        override fun doInBackground(vararg params: Void): Void? {
            patientsDao = instance.patientDao()
            calculationsDao = instance.calculationsDao()
            patientsDao.deleteAll()
            calculationsDao.deleteAll()
            return null
        }

        override fun onPostExecute(result: Void?) {
            listener.onCompleted()
        }
    }
}

interface OnDeleteCompleted {
    fun onCompleted()
}
