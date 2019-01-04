package com.medicalrecord.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface PatientsDao {

    @Query("SELECT * from patientsData")
    fun getAll(): LiveData<List<Patient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(patient: Patient): Long

    @Query("DELETE from patientsData")
    fun deleteAll()


}

@Dao
abstract class CalculationsDao {
    // Calculations
    @Transaction
    open fun insertCalculationWithValues(calculation: Calculation) {
        insertCalculation(calculation)
        updateWeight(calculation.patientId, calculation.weight)
        updateCalculationDate(calculation.patientId, calculation.date)
    }

    @Query("SELECT * from calculationsData")
    abstract fun getAll(): LiveData<List<Calculation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCalculation(calculation: Calculation)

    @Query("DELETE from calculationsData")
    abstract fun deleteAll()

    @Query("SELECT * from calculationsData WHERE patientId = :patientId")
    abstract fun getCalculationsByPatientId(patientId: Long): LiveData<List<Calculation>>

    // PatientData
    @Query("UPDATE patientsData SET weight = :weight WHERE id = :id")
    abstract fun updateWeight(id: Long, weight: Double)

    @Query("UPDATE patientsData SET date = :date WHERE id = :id")
    abstract fun updateCalculationDate(id: Long, date: String)
}
