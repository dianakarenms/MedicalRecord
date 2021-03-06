package com.medicalrecord.calcprenatal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.medicalrecord.adapters.PatientsAdapter
import com.medicalrecord.data.Patient
import com.medicalrecord.data.viewmodels.PatientViewModel
import com.medicalrecord.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private var viewModel: PatientViewModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)

        viewModel = ViewModelProviders.of(this).get(PatientViewModel::class.java)

        toolbarMainDoctorSettingsBtn.onClick { startActivity<DoctorDataActivity>() }
        mainAddPatientBtn.onClick { startActivity<AddPatientActivity>() }

        val adapter = PatientsAdapter {
            startActivity<CalculateValuesActivity>("patient" to it)
        }
        mainPatientsRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
        mainPatientsRecycler.adapter = adapter

        viewModel?.all?.observe(this, Observer<List<Patient>> { t -> adapter.setPatients(t!!) })

        if(Constants.getHashMap(Constants.BASE_VALUES, this@MainActivity) == null ) {
            Constants.saveHashMap(Constants.BASE_VALUES, Constants.baseValues, this@MainActivity)
        }
    }
}
