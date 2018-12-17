package com.medicalrecord.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(entities =
[
    Patient::class,
    Calculation::class
], version = 1)
@TypeConverters(Converters::class)
abstract class MedicalRecordDataBase : RoomDatabase() {

    abstract fun patientDao(): PatientsDao
    abstract fun calculationsDao(): CalculationsDao
    /*abstract fun solutionsDao(): SolutionsDao
    abstract fun aditionalDataDao(): AditionalDataDao
    abstract fun doctorReferenceDataDao(): DoctorReferenceDataDao*/

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