package com.medicalrecord.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface PatientsDao {

    /*fun insertPatientWithCaculation(patients: List<PatientData>) {
        for(patient in patients) {
            if(patient.calculations != null) {
                insertPetsForUser(patient, patient.calculations);
            }
        }
        insertAll(patients)
    }

    fun insertPetsForUser(patient: PatientData, calculations: List<CalculationData>){

        for(calculation in calculations){
            calculation.patientId = patient.id!!
        }

        _insertAll(calculations)
    }

    fun getUsersWithPetsEagerlyLoaded() : List<User>  {
        var usersWithPets: List<PatientsWithCalculations> = _loadUsersWithPets();
        List<User> users = new ArrayList<User>(usersWithPets.size())
        for(UserWithPets userWithPets: usersWithPets) {
            userWithPets.user.pets = userWithPets.pets;
            users.add(userWithPets.user);
        }
        return users;
    }*/

    @Query("SELECT * from patientsData")
    fun getAll(): LiveData<List<Patient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(patient: Patient): Long

    @Query("DELETE from patientsData")
    fun deleteAll()

    //@Query("SELECT last_insert_rowid()")
    //fun getLastInsertedId(): Long

    //@Query("SELECT * FROM calculationsData WHERE patientId = :patientId")
    //fun getCalculations(patientId: Long): List<CalculationData>

}

@Dao
abstract class CalculationsDao {
    @Transaction
    open fun insertCalculationWithValues(calculation: Calculation, solution: Solution) {
        calculation.solutionId = insertSolution(solution).toInt()
        insertCalculation(calculation)
    }

    /** Calculations **/
    @Query("SELECT * from calculationsData")
    abstract fun getAll(): LiveData<List<Calculation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCalculation(calculation: Calculation)

    @Query("DELETE from calculationsData")
    abstract fun deleteAll()

    @Query("SELECT * FROM calculationsData WHERE patientId=:patientId")
    abstract fun getCalculationsByPatientId(patientId: Int): LiveData<List<Calculation>>

    /** Solutions **/
    @Query("SELECT * from solutionsData")
    abstract fun getAllSolution(): LiveData<List<Solution>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSolution(solutionData: Solution) : Long

    @Query("DELETE from solutionsData")
    abstract fun deleteAllSolutions()

    @Query("SELECT * FROM solutionsData WHERE id=:id")
    abstract fun getSolutionById(id: Int): LiveData<Solution>

    @Query("SELECT last_insert_rowid()")
    abstract  fun getLastInsertedId(): Long
}

@Dao
interface SolutionsDao {
    @Query("SELECT * from solutionsData")
    fun getAll(): LiveData<List<Solution>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(solutionData: Solution) : Long

    @Query("DELETE from solutionsData")
    fun deleteAll()

    @Query("SELECT * FROM solutionsData WHERE id=:id")
    fun getSolutionById(id: Int): LiveData<Solution>

    @Query("SELECT last_insert_rowid()")
    fun getLastInsertedId(): Long
}

@Dao
interface AditionalDataDao {
    @Query("SELECT * from aditionalData")
    fun getAll(): List<AditionalData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(aditionalData: AditionalData)

    @Query("DELETE from aditionalData")
    fun deleteAll()

    @Query("SELECT * FROM aditionalData WHERE id=:id")
    fun getAditionalDataById(id: Int): AditionalData

    @Query("SELECT last_insert_rowid()")
    fun getLastInsertedId(): Long
}

@Dao
interface DoctorReferenceDataDao {
    @Query("SELECT * from doctorReferenceData")
    fun getAll(): List<DoctorReferenceData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(doctorReferenceData: DoctorReferenceData)

    @Query("DELETE from doctorReferenceData")
    fun deleteAll()

    @Query("SELECT * FROM doctorReferenceData WHERE id=:id")
    fun getDoctorReferenceDataById(id: Int): DoctorReferenceData

    @Query("SELECT last_insert_rowid()")
    fun getLastInsertedId(): Long
}