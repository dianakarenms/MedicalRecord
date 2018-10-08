package com.medicalrecord.calcprenatal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.medicalrecord.data.PatientData
import kotlinx.android.synthetic.main.activity_calculate_values.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * Created by diana.munoz on 10/1/18.
 */
class CalculateValuesActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_values)

        setSupportActionBar(calculateValuesToolbar)

        val patient = intent.getSerializableExtra("patient") as PatientData
        calculateValuesWeightTxt.text = "${patient.weight} Kg"

        calculateValuesCreateBtn.onClick {
            startActivity<AditionalInstrActivity>()
        }

        toolbarCalculateValuesEditBtn.onClick {
            startActivity<EditValuesActivity>()
        }

    }
}
