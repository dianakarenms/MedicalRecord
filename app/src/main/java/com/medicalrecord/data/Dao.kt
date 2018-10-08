package com.medicalrecord.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface PatientsDataDao {
    @Query("SELECT * from patientsData")
    fun getAll(): List<PatientData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(patientData: PatientData)

    @Query("DELETE from patientsData")
    fun deleteAll()

    @Query("SELECT last_insert_rowid()")
    fun getLastInsertedId(): Long
}

@Dao
interface CalculationDataDao {
    @Query("SELECT * from calculationsData")
    fun getAll(): List<CalculationData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(calculationData: CalculationData)

    @Query("DELETE from calculationsData")
    fun deleteAll()

    @Query("SELECT * FROM calculationsData WHERE patientId=:patientId")
    fun getCalculationsPerPatient(patientId: Long): List<CalculationData>
}