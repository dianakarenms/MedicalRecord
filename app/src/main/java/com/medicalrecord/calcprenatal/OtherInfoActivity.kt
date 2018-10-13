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
            patient.name = intent.getStringExtra("name")
            patient.record = otherInfoExpEdit.text.toString()
            patient.bed = otherInfoBedEdit.text.toString()
            patient.gestation = otherInfoGestationEdit.text.toString().toInt()
            patient.dx = otherInfoDxEdit.text.toString()
            patient.date = "vacío"
            patient.weight = intent.getStringExtra("weight").toDouble()
            //patientData.date = Calendar.getInstance().time.formatted

            mPatientVM?.insert(patient)
            backToHome()
        }

        otherInfoCancelBtn.onClick { backToHome() }
    }

    private fun backToHome() {
        startActivity(intentFor<MainActivity>().clearTop().clearTask())
        finish()
    }
}
