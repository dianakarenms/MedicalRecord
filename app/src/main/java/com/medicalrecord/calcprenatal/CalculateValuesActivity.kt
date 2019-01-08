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
import com.medicalrecord.adapters.ExpandableValuesAdapter
import com.medicalrecord.data.*
import com.medicalrecord.data.viewmodels.CalculationViewModel
import com.medicalrecord.utils.*
import com.medicalrecord.utils.Constants.Companion.BASE_VALUES
import com.medicalrecord.utils.Constants.Companion.getHashMap
import kotlinx.android.synthetic.main.activity_calculate_values.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.math.round

/**
 * Created by diana.munoz on 10/1/18.
 */
class CalculateValuesActivity: AppCompatActivity() {

    private var viewModel: CalculationViewModel? = null
    private var adapter: ExpandableValuesAdapter? = null
    private val valuesTypeList: List<String> = listOf(Constants.SOLUTION, Constants.ADDITIONAL_INFO, Constants.DOCTOR_REFERENCE)

    private lateinit var patient: Patient
    private lateinit var dv: LinkedHashMap<String, Double>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_values)
        setSupportActionBar(calculateValuesToolbar)

        adapter = ExpandableValuesAdapter { refValue, position ->
            //toast("clicked ${refValue.name}")
            //showEditValueDialog(refValues, position)
        }
        calculateValuesItemRecycler.adapter = adapter
        calculateValuesItemRecycler.layoutManager = LinearLayoutManager(this@CalculateValuesActivity)

        calculateValuesWeightWrapper.onClick { showEditWeightDialog() }

        patient = intent.getSerializableExtra("patient") as Patient
        dv = getHashMap( BASE_VALUES, this@CalculateValuesActivity )!!

        toolbarCalculateValuesEditBtn.onClick { startActivity<EditValuesActivity>() }
        calculateValuesCreateBtn.onClick { startActivity<AditionalInstrActivity>() }
        calculateValuesCalculateBtn.onClick {
            // La magia comienza
            val weight = patient.weight
            val calculation = Calculation(null, patient.id!!,  Calendar.getInstance().time.formatted, weight)

            calculation.refValues?.addAll(solutions())
            viewModel?.insert(calculation)
        }

        viewModel = ViewModelProviders.of(this, CustomViewModelFactory(this.application, patient.id!!)).get(CalculationViewModel::class.java)

        viewModel?.getCalculationsByPatientId(patient.id!!)?.observe(this@CalculateValuesActivity, Observer<List<Calculation>> { calculations ->
                if (calculations?.isNotEmpty()!!) {
                    hideEmptyStateView()
                    calculations.last().refValues?.let { adapter!!.setValueListAndType(it, valuesTypeList) }
                } else {
                    showEmptyStateView()
                }
            }
        )

        calculateValuesWeightTxt.text = "${patient.weight}"
    }

    private fun showEmptyStateView() {
        calculateValuesItemRecycler.visibility = View.GONE
        calculateValuesListRecycler.visibility = View.GONE
        calculateValuesEmptyTxt.visibility = View.VISIBLE
    }

    private fun hideEmptyStateView() {
        calculateValuesItemRecycler.visibility = View.VISIBLE
        calculateValuesListRecycler.visibility = View.VISIBLE
        calculateValuesEmptyTxt.visibility = View.GONE
    }

    private fun solutions(): MutableList<RefValue> {
        // region Solutions
        val weight = patient.weight
        val proteins = maxOf(dv["prot_10"]!!, dv["prot_8"]!!)
        var ps = Solution()
        ps.líquidos_iv_tot = round(100 * (weight * dv["líquidos"]!!)) / 100
        ps.trophamine_10 = round(100 * (weight * dv["prot_10"]!! / 0.1)) / 100
        ps.trophamine_8 = round(100 * (weight * dv["prot_8"]!! / 0.08)) / 100
        ps.intralipid_20 = round(100 * (weight * dv["lípidos"]!! * 5)) / 100
        ps.sg_50 = round(100 * (weight * dv["chs_50"]!! * 100 / 50)) / 100
        ps.sg_10 = round(100 * (weight * dv["chs_10"]!! * 100 / 10)) / 100
        ps.kcl_amp_10 = round(100 * (weight * dv["kcl_amp_10_"]!! / 2)) / 100
        ps.kcl_amp_5 = round(100 * (weight * dv["kcl_amp_5_"]!! / 4)) / 100
        ps.naclhip = round(100 * (weight * dv["naclhip_"]!! / 3)) / 100
        ps.sol_fisiológica = round(100 * (weight * dv["sol_fisiológica_"]!! * 100 / 15.4)) / 100
        ps.fosfato_k = round(100 * (weight * dv["fosfato_k_"]!! / 1.102)) / 100
        ps.glucca = round(100 * (weight * dv["calcio_"]!! / 100) * 0.464) / 100
        ps.magnesio = round(100 * (weight * dv["magnesio_"]!! / 100) * 0.81) / 100
        ps.gluccaMl = round(100 * ps.glucca / 0.464) / 100
        ps.magnesioMl = round(100 * ps.magnesio / 2.025 ) / 100
        ps.mvi = if (1.7 * weight > 5) 5.0 else round(100 * (1.7 * weight)) / 100
        ps.oligoelementos = round(100 * (dv["oligoelementos_"]!! * weight)) / 100
        ps.l_cisteína = round(100 * (proteins * weight * 100 / 2.5)) / 100
        ps.carnitina = round(100 * (dv["carnitina_"]!! * weight)) / 100
        if (patient.gestation >= 32) {
            ps.heparina = 0.0
        } else {
            ps.heparina = round(100 * (dv["heparina_"]!! * ps.intralipid_20)) / 100
        }
        val sum = round( 100 * (ps.oligoelementos + ps.mvi + ps.magnesio + ps.glucca + ps.fosfato_k + ps.naclhip + ps.sol_fisiológica + ps.trophamine_10 + ps.trophamine_8 + ps.sg_50 + ps.sg_10 + ps.kcl_amp_10 + ps.kcl_amp_5 + ps.intralipid_20)) / 100
        ps.abd = round(100 * (ps.líquidos_iv_tot - sum)) / 100

        var refValues = mutableListOf<RefValue>()
        refValues.add(RefValue("líquidos_iv_tot", ps.líquidos_iv_tot, Constants.SOLUTION))
        refValues.add(RefValue("sol_fisiológica", ps.sol_fisiológica, Constants.SOLUTION))
        refValues.add(RefValue("trophamine_10", ps.trophamine_10, Constants.SOLUTION))
        refValues.add(RefValue("trophamine_8", ps.trophamine_8, Constants.SOLUTION))
        refValues.add(RefValue("intralipid_20", ps.intralipid_20, Constants.SOLUTION))
        refValues.add(RefValue("sg_50", ps.sg_50, Constants.SOLUTION))
        refValues.add(RefValue("sg_10", ps.sg_10, Constants.SOLUTION))
        refValues.add(RefValue("kcl_amp_10", ps.kcl_amp_10, Constants.SOLUTION))
        refValues.add(RefValue("kcl_amp_5", ps.kcl_amp_5, Constants.SOLUTION))
        refValues.add(RefValue("naclhip", ps.naclhip, Constants.SOLUTION))
        refValues.add(RefValue("fosfato_k", ps.fosfato_k, Constants.SOLUTION))
        refValues.add(RefValue("mvi", ps.mvi, Constants.SOLUTION))
        refValues.add(RefValue("oligoelementos", ps.oligoelementos, Constants.SOLUTION))
        refValues.add(RefValue("l_cisteína", ps.l_cisteína, Constants.SOLUTION))
        refValues.add(RefValue("carnitina", ps.carnitina, Constants.SOLUTION))
        refValues.add(RefValue("heparina", ps.heparina, Constants.SOLUTION))
        refValues.add(RefValue("abd", ps.abd, Constants.SOLUTION))
        refValues.add(RefValue("glucca", ps.glucca, Constants.SOLUTION))
        refValues.add(RefValue("magnesio", ps.magnesio, Constants.SOLUTION))
        refValues.add(RefValue("gluccaMl", ps.gluccaMl, Constants.SOLUTION))
        refValues.add(RefValue("magnesioMl", ps.magnesioMl, Constants.SOLUTION))
        // endregion

        // region Additional Data
        var pad = AdditionalInfo()
        val carbs = maxOf(dv["chs_50"]!!, dv["chs_10"]!!)

        pad.líquidos_tot = ps.líquidos_iv_tot
        pad.calorías_tot = round(100 * ((carbs * 3.4 * weight) + (dv["lípidos"]!! * 11 * weight) + (proteins * 4 * weight))) / 100
        pad.caljjml = round(100 * (pad.calorías_tot / pad.líquidos_tot)) / 100
        pad.caljjkgjjdia = round(100 * (pad.calorías_tot / weight)) / 100
        pad.infusión = round(100 * (pad.líquidos_tot / 24)) / 100
        pad.nitrógeno = round(100 * (proteins * weight / 6.25)) / 100
        pad.relcnpjntg = round(100 * (((carbs * 3.4) + (dv["lípidos"]!! * 11)) / pad.nitrógeno)) / 100
        pad.concentración = round(100 * (carbs * weight / pad.líquidos_tot * 100)) / 100
        pad.gkm = round(100 * (carbs * 1000 / 1440)) / 100
        pad.calprot = round(100 * ((proteins * 4 * weight * 100) / pad.calorías_tot)) / 100
        pad.calgrasa = round(100 * ((dv["lípidos"]!! * 11 * 100 * weight) / pad.calorías_tot)) / 100
        pad.calchs50 = round(100 * ((dv["chs_50"]!! * 3.4 * weight * 100) / pad.calorías_tot)) / 100
        pad.calchs10 = round(100 * ((dv["chs_10"]!! * 3.4 * weight * 100) / pad.calorías_tot)) / 100

        refValues.add(RefValue("líquidos_tot", pad.líquidos_tot, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("calorías_tot", pad.calorías_tot, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("caljjml", pad.caljjml, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("caljjkgjjdia", pad.caljjkgjjdia, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("infusión", pad.infusión, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("nitrógeno", pad.nitrógeno, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("relcnpjntg", pad.relcnpjntg, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("concentración", pad.concentración, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("gkm", pad.gkm, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("calprot", pad.calprot, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("calgrasa", pad.calgrasa, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("calchs50", pad.calchs50, Constants.ADDITIONAL_INFO))
        refValues.add(RefValue("calchs10", pad.calchs10, Constants.ADDITIONAL_INFO))
        // endregion

        // region Reference per Doctor
        getHashMap(BASE_VALUES, this@CalculateValuesActivity).let {
            it ->
            if (it != null) {
                for((key, value) in it) {
                    val refValue = RefValue(key, value, Constants.DOCTOR_REFERENCE)
                    refValues.add(refValue)
                }
            }
        }
        // endregion

        return refValues
    }

    private fun showEditWeightDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_value_field, null)

        var etComments: EditText = view.findViewById(R.id.etComments)
        etComments.text = patient.weight.toString().editable
        etComments.showKeyboard()

        var alertDialog = AlertDialog.Builder(this@CalculateValuesActivity).create()
        alertDialog.setCancelable(false)
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
