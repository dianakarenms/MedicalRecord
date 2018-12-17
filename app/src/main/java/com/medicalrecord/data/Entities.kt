package com.medicalrecord.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.medicalrecord.calcprenatal.RefValue
import java.io.Serializable

// PATIENTS
@Entity(tableName = "patientsData")
data class Patient(@PrimaryKey(autoGenerate = true) var id: Long?,
                   var name: String,
                   var record: String,
                   var bed: String,
                   var gestation: Int,
                   var dx: String,
                   var weight: Double,
                   var date: String

):Serializable {
    constructor():this(null,"","","",0, "", 0.0, "")
}

// CALCULATIONS
@Entity(tableName = "calculationsData",
        foreignKeys = [
            ForeignKey(entity = Patient::class, parentColumns = ["id"], childColumns = ["patientId"], onDelete = CASCADE)
        ])
data class Calculation(@PrimaryKey(autoGenerate = true) var id: Long?,
                       var patientId: Long,
                       var date: String,
                       var weight: Double,
                       var refValues: MutableList<RefValue>? = arrayListOf()


){
    constructor():this(null,0,"",0.0, arrayListOf<RefValue>())
}

class Converters {
    @TypeConverter
    fun listToJson(value: MutableList<RefValue>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): MutableList<RefValue>? {
        val objects = Gson().fromJson(value, Array<RefValue>::class.java) as Array<RefValue>
        val list = objects.toMutableList()
        return list
    }
}