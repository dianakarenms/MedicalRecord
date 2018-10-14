package com.medicalrecord.calcprenatal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.Patient
import com.medicalrecord.data.viewmodels.CalculationViewModel
import com.medicalrecord.utils.CustomViewModelFactory
import kotlinx.android.synthetic.main.activity_calculate_values.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * Created by diana.munoz on 10/1/18.
 */
class CalculateValuesActivity: AppCompatActivity() {

    private var viewModel: CalculationViewModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_values)
        setSupportActionBar(calculateValuesToolbar)

        calculateValuesCreateBtn.onClick { startActivity<AditionalInstrActivity>() }
        toolbarCalculateValuesEditBtn.onClick { startActivity<EditValuesActivity>() }

        val patient = intent.getSerializableExtra("patient") as Patient

        viewModel = ViewModelProviders.of(this, CustomViewModelFactory(this.application, patient.id!!)).get(CalculationViewModel::class.java)
        viewModel?.all?.observe(this, Observer<List<Calculation>> { t ->
                if(t?.isNotEmpty()!!) {
                    calculateValuesWrapper.visibility = View.VISIBLE
                    calculateValuesCalculationsRecycler.visibility = View.VISIBLE
                    calculateValuesEmptyTxt.visibility = View.GONE
                } else {
                    calculateValuesWrapper.visibility = View.GONE
                    calculateValuesCalculationsRecycler.visibility = View.GONE
                    calculateValuesEmptyTxt.visibility = View.VISIBLE
                }
            }
        )

        calculateValuesWeightTxt.text = "${patient.weight} Kg"
    }
}
