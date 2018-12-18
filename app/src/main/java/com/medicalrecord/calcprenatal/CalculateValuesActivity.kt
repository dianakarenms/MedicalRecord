package com.medicalrecord.calcprenatal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.EditText
import com.medicalrecord.adapters.ValuesAdapter
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.Patient
import com.medicalrecord.data.RefValue
import com.medicalrecord.data.Solution
import com.medicalrecord.data.viewmodels.CalculationViewModel
import com.medicalrecord.utils.*
import com.medicalrecord.utils.Constants.Companion.BASE_VALUES
import com.medicalrecord.utils.Constants.Companion.getHashMap
import kotlinx.android.synthetic.main.activity_calculate_values.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

/**
 * Created by diana.munoz on 10/1/18.
 */
class CalculateValuesActivity: AppCompatActivity() {

    private var viewModel: CalculationViewModel? = null
    private var adapter: ValuesAdapter? = null

    private lateinit var patient: Patient
    private lateinit var dv: LinkedHashMap<String, Double>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_values)
        setSupportActionBar(calculateValuesToolbar)

        adapter = ValuesAdapter() {refValue, position ->
            toast("clicked ${refValue.name}")
            //showEditValueDialog(refValues, position)
        }
        calculateValuesRecycler1.adapter = adapter
        calculateValuesRecycler1.layoutManager = LinearLayoutManager(this@CalculateValuesActivity)

        calculateValuesWeightTxt.onClick { showEditWeightDialog() }

        patient = intent.getSerializableExtra("patient") as Patient
        dv = getHashMap( BASE_VALUES, this@CalculateValuesActivity )!!

        toolbarCalculateValuesEditBtn.onClick { startActivity<EditValuesActivity>() }
        calculateValuesCreateBtn.onClick { startActivity<AditionalInstrActivity>() }
        calculateValuesCalculateBtn.onClick {
            // La magia comienza
            val weight = patient.weight
            val calculation = Calculation(null, patient.id!!,  Calendar.getInstance().time.formatted, weight)


            val carbs = maxOf(dv["chs_50"]!!, dv["chs_10"]!!)
            val liquids = maxOf(dv["naclhip_"]!!, dv["sol_fisiológica_"]!!)
            val potasium = maxOf(maxOf(dv["kcl_amp_10_"]!!,dv["kcl_amp_5_"]!!), dv["fosfato_k_"]!!)

            /*ps.líquidos_tot = ps.líquidos_iv_tot
            ps.calorías_tot = Double(round(100 * ((carbs * 3.4) + (dv.lípidos * 11) + (proteins * 4))) / 100)
            ps.caljjml = Double(round(100 * (ps.calorías_tot / ps.líquidos_tot)) / 100)
            ps.caljjkgjjdia = Double(round(100 * (ps.calorías_tot / weight)) / 100)
            ps.infusión = Double(round(100 * (ps.líquidos_tot / 24)) / 100)
            ps.nitrógeno = Double(round(100 * (proteins * weight / 6.25)) / 100)
            ps.relcnpjntg = Double(round(100 * (((carbs * 3.4) + (dv.lípidos * 11)) / ps.nitrógeno)) / 100)
            ps.concentración = Double(round(100 * (carbs * weight / ps.líquidos_tot * 100)) / 100)
            ps.gkm = Double(round(100 * (carbs * 1000 / 1440)) / 100)
            ps.calprot = Double(round(100 * ((proteins * 4 * 100) / ps.calorías_tot)) / 100)
            ps.calgrasa = Double(round(100 * ((dv.lípidos * 11 * 100) / ps.calorías_tot)) / 100)
            ps.calchs50 = Double(round(100 * ((dv.chs_50 * 3.4 * 100) / ps.calorías_tot)) / 100)
            ps.calchs10 = Double(round(100 * ((dv.chs_10 * 3.4 * 100) / ps.calorías_tot)) / 100)*/
            calculation.refValues?.addAll(solutions())
            viewModel?.insert(calculation)
        }

        viewModel = ViewModelProviders.of(this, CustomViewModelFactory(this.application, patient.id!!)).get(CalculationViewModel::class.java)

        viewModel?.getCalculationsByPatientId(patient.id!!)?.observe(this@CalculateValuesActivity, Observer<List<Calculation>> { calculations ->
                if (calculations?.isNotEmpty()!!) {
                    hideEmptyStateView()
                    calculations.last().refValues?.let { adapter!!.setRefValues(it) }
                } else {
                    showEmptyStateView()
                }
            }
        )

        calculateValuesWeightTxt.text = "${patient.weight}"
    }

    private fun showEmptyStateView() {
        calculateValuesWrapper.visibility = View.GONE
        calculateValuesCalculationsRecycler.visibility = View.GONE
        calculateValuesEmptyTxt.visibility = View.VISIBLE
    }

    private fun hideEmptyStateView() {
        calculateValuesWrapper.visibility = View.VISIBLE
        calculateValuesCalculationsRecycler.visibility = View.VISIBLE
        calculateValuesEmptyTxt.visibility = View.GONE
    }

    // region Calculations
    fun solutions(): MutableList<RefValue> {
        val weight = patient.weight
        val proteins = maxOf(dv["prot_10"]!!, dv["prot_8"]!!)
        var ps = Solution()
        ps.líquidos_iv_tot = weight * dv["líquidos"]!!
        ps.trophamine_10 = weight * dv["prot_10"]!! / 0.1
        ps.trophamine_8 = weight * dv["prot_8"]!! / 0.08
        ps.intralipid_20 = weight * dv["lípidos"]!! * 5
        ps.sg_50 = weight * dv["chs_50"]!! * 1440 / 500
        ps.sg_10 = weight * dv["chs_10"]!! * 1440 / 100
        ps.kcl_amp_10 = weight * dv["kcl_amp_10_"]!! / 2
        ps.kcl_amp_5 = weight * dv["kcl_amp_5_"]!! / 4
        ps.naclhip = weight * dv["naclhip_"]!! / 3
        ps.sol_fisiológica = weight * dv["sol_fisiológica_"]!! * 100 / 15.4
        ps.fosfato_k = weight * dv["fosfato_k_"]!! / 1.102
        ps.glucca = weight * dv["calcio_"]!! / 100
        ps.magnesio = weight * dv["magnesio_"]!! * 0.01
        ps.mvi = if((1.7 * weight) > 5) { 5.0 } else { 1.7 * weight}
        ps.oligoelementos = dv["oligoelementos_"]!! * weight
        ps.l_cisteína = if((proteins * weight * 100 / 2.5) > 100) { 100.0 } else { proteins * weight * 100 / 2.5 }
        ps.carnitina = dv["carnitina_"]!! * weight
        if (patient.gestation >= 32) {
            ps.heparina = 0.0
        }else {
            ps.heparina = dv["heparina_"]!! * ps.intralipid_20
        }
        val sum = ps.oligoelementos + ps.mvi + ps.magnesio + ps.glucca + ps.fosfato_k + ps.naclhip + ps.sol_fisiológica + ps.trophamine_10 + ps.trophamine_8 + ps.sg_50 + ps.sg_10 + ps.kcl_amp_10 + ps.kcl_amp_5 + ps.intralipid_20
        ps.abd = ps.líquidos_iv_tot - sum

        // REF VALUES LIST
        var solutionRefValues = mutableListOf<RefValue>()
        solutionRefValues.add(RefValue("líquidos_iv_tot", ps.líquidos_iv_tot, Constants.SOLUTION))
        solutionRefValues.add(RefValue("sol_fisiológica", ps.sol_fisiológica, Constants.SOLUTION))
        solutionRefValues.add(RefValue("trophamine_10", ps.trophamine_10, Constants.SOLUTION))
        solutionRefValues.add(RefValue("trophamine_8", ps.trophamine_8, Constants.SOLUTION))
        solutionRefValues.add(RefValue("intralipid_20", ps.intralipid_20, Constants.SOLUTION))
        solutionRefValues.add(RefValue("sg_50", ps.sg_50, Constants.SOLUTION))
        solutionRefValues.add(RefValue("sg_10", ps.sg_10, Constants.SOLUTION))
        solutionRefValues.add(RefValue("kcl_amp_10", ps.kcl_amp_10, Constants.SOLUTION))
        solutionRefValues.add(RefValue("kcl_amp_5", ps.kcl_amp_5, Constants.SOLUTION))
        solutionRefValues.add(RefValue("naclhip", ps.naclhip, Constants.SOLUTION))
        solutionRefValues.add(RefValue("fosfato_k", ps.fosfato_k, Constants.SOLUTION))
        solutionRefValues.add(RefValue("glucca", ps.glucca, Constants.SOLUTION))
        solutionRefValues.add(RefValue("magnesio", ps.magnesio, Constants.SOLUTION))
        solutionRefValues.add(RefValue("mvi", ps.mvi, Constants.SOLUTION))
        solutionRefValues.add(RefValue("oligoelementos", ps.oligoelementos, Constants.SOLUTION))
        solutionRefValues.add(RefValue("l_cisteína", ps.l_cisteína, Constants.SOLUTION))
        solutionRefValues.add(RefValue("carnitina", ps.carnitina, Constants.SOLUTION))
        solutionRefValues.add(RefValue("heparina", ps.heparina, Constants.SOLUTION))
        solutionRefValues.add(RefValue("abd", ps.abd, Constants.SOLUTION))
        return solutionRefValues
    }
    // endregion

    private fun showEditWeightDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_value_field, null)

        var etComments: EditText = view.findViewById(R.id.etComments)
        etComments.text = patient.weight.toString().editable
        etComments.showKeyboard()

        var alertDialog = AlertDialog.Builder(this@CalculateValuesActivity).create()
        alertDialog.setCancelable(true)
        alertDialog.setMessage("Peso Actual")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialogInterface: DialogInterface, i: Int ->
            val newVal = etComments.text.toString().toDouble().decimalsFormat(1)
            calculateValuesWeightTxt.text = newVal
            patient.weight = newVal.toDouble()
            etComments.hideKeyboard()
            alertDialog.dismiss()
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialogInterface: DialogInterface, i: Int ->
            etComments.hideKeyboard()
            alertDialog.dismiss()
        }

        alertDialog.setView(view)
        alertDialog.show()
    }
}
