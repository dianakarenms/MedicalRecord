package com.medicalrecord.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "patientsData")
data class PatientData(@PrimaryKey(autoGenerate = true) var id: Long?,
                       var name: String,
                       var record: String,
                       var bed: String,
                       var gestation: Long,
                       var dx: String
                       //@Ignore @ColumnInfo(name = "cloud") var cloud: String

){
    constructor():this(null,"","","",0, "")
}

@Entity(tableName = "calculationsData",
        foreignKeys = [
            ForeignKey(entity = PatientData::class, parentColumns = ["id"], childColumns = ["patientId"], onDelete = CASCADE)
        ])
data class CalculationData(@PrimaryKey(autoGenerate = true) var id: Long?,
                       var patientId: Long,
                       var date: String,
                       var weight: Double

){
    constructor():this(null,0,"",0.0)
}
