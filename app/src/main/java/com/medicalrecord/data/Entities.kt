package com.medicalrecord.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

// PATIENTS
@Entity(tableName = "patientsData")
data class Patient(@PrimaryKey(autoGenerate = true) var id: Int?,
                   var name: String,
                   var record: String,
                   var bed: String,
                   var gestation: Int,
                   var dx: String,
                   var weight: Double,
                   var date: String
                        //@Ignore var calculations: List<CalculationData>

):Serializable {
    constructor():this(null,"","","",0, "", 0.0, "")
}

// CALCULATIONS
@Entity(tableName = "calculationsData",
        foreignKeys = [
            ForeignKey(entity = Patient::class, parentColumns = ["id"], childColumns = ["patientId"], onDelete = CASCADE),
            ForeignKey(entity = SolutionsData::class, parentColumns = ["id"], childColumns = ["solutionId"], onDelete = CASCADE),
            ForeignKey(entity = AditionalData::class, parentColumns = ["id"], childColumns = ["additionalId"], onDelete = CASCADE),
            ForeignKey(entity = DoctorReferenceData::class, parentColumns = ["id"], childColumns = ["doctorId"], onDelete = CASCADE)
        ])
data class CalculationData(@PrimaryKey(autoGenerate = true) var id: Int?,
                           var patientId: Int,
                           var date: String,
                           var weight: Double,
                           var solutionId: Int,
                           var additionalId: Int,
                           var doctorId: Int


){
    constructor():this(null,0,"",0.0, 0, 0, 0)
}

// SOLUTIONS
@Entity(tableName = "solutionsData")
data class SolutionsData(@PrimaryKey(autoGenerate = true) var id: Int?,
                         var líquidos_iv_tot: Double,
                         var sol_fisiológica: Double,
                         var trophamine_10: Double,
                         var trophamine_8: Double,
                         var intralipid_20: Double,
                         var sg_50: Double,
                         var sg_10: Double,
                         var kcl_amp_10: Double,
                         var kcl_amp_5: Double,
                         var naclhip: Double,
                         var fosfato_k: Double,
                         var glucca: Double,
                         var magnesio: Double,
                         var mvi: Double,
                         var oligoelementos: Double,
                         var l_cisteína: Double,
                         var carnitina: Double,
                         var heparina: Double,
                         var abd: Double
){
    constructor():this(null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0)
}

// ADITIONAL DATA
@Entity(tableName = "aditionalData")
data class AditionalData(@PrimaryKey(autoGenerate = true) var id: Int?,
                         var líquidos_tot: Double,
                         var calorías_tot: Double,
                         var caljjml: Double,
                         var caljjkgjjdia: Double,
                         var infusión: Double,
                         var nitrógeno: Double,
                         var relcnpjntg: Double,
                         var concentración: Double,
                         var gkm: Double,
                         var calprot: Double,
                         var calgrasa: Double,
                         var calchs50: Double,
                         var calchs10: Double
){
    constructor():this(null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
}

// DOCTOR REFERENCE
@Entity(tableName = "doctorReferenceData")
data class DoctorReferenceData(@PrimaryKey(autoGenerate = true) var id: Int?,
                         var líquidos: Double,
                         var sol_fisiológica_: Double,
                         var naclhip_: Double,
                         var prot_10: Double,
                         var prot_8: Double,
                         var chs_50: Double,
                         var chs_10: Double,
                         var fosfato_k: Double,
                         var kcl_amp_10_: Double,
                         var kcl_amp_5_: Double,
                         var lípidos: Double,
                         var calcio_: Double,
                         var magnesio_: Double,
                         var mvi_: Double,
                         var oligoelementos_: Double,
                         var carnitina_: Double,
                         var heparina_: Double
){
    constructor():this(null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0)
}