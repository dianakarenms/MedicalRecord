package com.medicalrecord.utils

import com.medicalrecord.data.AditionalData
import com.medicalrecord.data.DoctorReferenceData
import com.medicalrecord.data.SolutionsData

class Constants {
    companion object {
        var solutions = SolutionsData(
                null,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0, 0.0,
                3.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
        )

        var aditionalData = AditionalData(
                null,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
        )

        var doctorReference = DoctorReferenceData(
                null,
                150.0,
                0.0,
                0.0,
                3.5,
                0.0,
                8.5,
                0.0,
                0.0,
                3.0,
                0.0,
                3.5,
                100.0,
                40.0,
                3.0,
                1.5,
                1.5,
                0.5
        )
    }
}