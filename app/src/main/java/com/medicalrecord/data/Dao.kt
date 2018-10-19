package com.medicalrecord.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

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
interface CalculationsDao {
    /*fun insertCalculationWithValues(calculation: Calculation, solution: Solution) {
        calculation.solutionId = insertSolution(solution).toInt()
        insert(calculation)
    }*/

    @Query("SELECT * from calculationsData")
    fun getAll(): LiveData<List<Calculation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(calculation: Calculation)

    @Query("DELETE from calculationsData")
    fun deleteAll()

    @Query("SELECT * FROM calculationsData WHERE patientId=:patientId")
    fun getCalculationsByPatientId(patientId: Int): LiveData<List<Calculation>>

    /*@Query("SELECT * from solutionsData")
    fun getAllSolution(): LiveData<List<Solution>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSolution(solutionData: Solution) : Long

    @Query("DELETE from solutionsData")
    fun deleteAllSolutions()

    @Query("SELECT * FROM solutionsData WHERE id=:id")
    fun getSolutionById(id: Int): LiveData<Solution>

    @Query("SELECT last_insert_rowid()")
    fun getLastInsertedId(): Long*/
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