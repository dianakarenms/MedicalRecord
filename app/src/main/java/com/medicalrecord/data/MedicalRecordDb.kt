package com.medicalrecord.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [PatientData::class, CalculationData::class], version = 1)
abstract class MedicalRecordDataBase : RoomDatabase() {

    abstract fun patientDataDao(): PatientsDataDao
    abstract fun calculationDataDao(): CalculationDataDao

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
    }
}