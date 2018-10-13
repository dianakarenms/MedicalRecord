package com.medicalrecord.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface PatientsDataDao {

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
    fun getAll(): List<PatientData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(patientData: PatientData): Long

    @Query("DELETE from patientsData")
    fun deleteAll()

    //@Query("SELECT last_insert_rowid()")
    //fun getLastInsertedId(): Long

    //@Query("SELECT * FROM calculationsData WHERE patientId = :patientId")
    //fun getCalculations(patientId: Long): List<CalculationData>

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
    fun getCalculationsPerPatient(patientId: Int): List<CalculationData>
}