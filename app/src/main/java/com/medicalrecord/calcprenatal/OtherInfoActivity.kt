package com.medicalrecord.calcprenatal

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.medicalrecord.data.Patient
import com.medicalrecord.data.viewmodels.PatientViewModel
import kotlinx.android.synthetic.main.activity_other_info.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Created by diana.munoz on 10/1/18.
 */
class OtherInfoActivity : AppCompatActivity() {

    private var mPatientVM: PatientViewModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        setSupportActionBar(otherInfoToolbar)

        mPatientVM = ViewModelProviders.of(this).get(PatientViewModel::class.java)

        otherInfoSaveBtn.onClick {
            val patient = Patient()
            var flag = 0
            val record = if(otherInfoExpEdit.text.isNotBlank()) { otherInfoExpEdit.text }
                    else {
                        flag++
                        otherInfoExpEdit.error = "Requerido"
                    }
            val bed = if(otherInfoBedEdit.text.isNotBlank()) { otherInfoBedEdit.text }
                    else {
                        flag++
                        otherInfoBedEdit.error = "Requerido"
                    }
            val gestation = if(otherInfoGestationEdit.text.isNotBlank()) { otherInfoGestationEdit.text }
                    else {
                        flag++
                        otherInfoGestationEdit.error = "Requerido"
                    }
            val dx = if(otherInfoDxEdit.text.isNotBlank()) { otherInfoDxEdit.text }
                    else {
                        flag++
                        otherInfoDxEdit.error = "Requerido"
                    }

            if( flag == 0) {
                patient.name = intent.getStringExtra("name")
                patient.record = record.toString()
                patient.bed = bed.toString()
                patient.gestation = gestation.toString().toInt()
                patient.dx = dx.toString()
                patient.date = "vacío"
                patient.weight = intent.getStringExtra("weight").toDouble()
                mPatientVM?.insert(patient)
                backToHome()
            }
        }

        otherInfoCancelBtn.onClick { backToHome() }
    }

    private fun backToHome() {
        startActivity(intentFor<MainActivity>().clearTop().clearTask())
        finish()
    }
}
