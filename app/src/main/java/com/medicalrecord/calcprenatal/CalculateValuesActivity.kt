package com.medicalrecord.calcprenatal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.medicalrecord.data.Calculation
import com.medicalrecord.data.Patient
import com.medicalrecord.data.Solution
import com.medicalrecord.data.viewmodels.CalculationViewModel
import com.medicalrecord.utils.Constants.Companion.BASE_VALUES
import com.medicalrecord.utils.Constants.Companion.getHashMap
import com.medicalrecord.utils.CustomViewModelFactory
import com.medicalrecord.utils.formatted
import kotlinx.android.synthetic.main.activity_calculate_values.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.lang.Math.round
import java.util.*

/**
 * Created by diana.munoz on 10/1/18.
 */
class CalculateValuesActivity: AppCompatActivity() {

    private var viewModel: CalculationViewModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_values)
        setSupportActionBar(calculateValuesToolbar)

        val patient = intent.getSerializableExtra("patient") as Patient
        val dv = getHashMap( BASE_VALUES, this@CalculateValuesActivity )!!

        toolbarCalculateValuesEditBtn.onClick { startActivity<EditValuesActivity>() }
        calculateValuesCreateBtn.onClick { startActivity<AditionalInstrActivity>() }
        calculateValuesCalculateBtn.onClick {
            // La magia comienza
            var ps = Solution()
            val weight = patient.weight
            val calculation = Calculation(null, patient.id!!,  Calendar.getInstance().time.formatted, weight, null, null, null )

            val proteins = maxOf(dv["prot_10"]!!, dv["prot_8"]!!)
            val carbs = maxOf(dv["chs_50"]!!, dv["chs_10"]!!)
            val liquids = maxOf(dv["naclhip_"]!!, dv["sol_fisiológica_"]!!)
            val potasium = maxOf(maxOf(dv["kcl_amp_10_"]!!,dv["kcl_amp_5_"]!!), dv["fosfato_k_"]!!)

            ps.líquidos_iv_tot = (round(100 * (weight * dv["líquidos"]!!)) / 100).toDouble()
            ps.trophamine_10 = (round(100 * (weight * dv["prot_10"]!! / 0.1)) / 100).toDouble()
            ps.trophamine_8 = (round(100 * (weight * dv["prot_8"]!! / 0.08)) / 100).toDouble()
            ps.intralipid_20 = (round(100 * (weight * dv["lípidos"]!! * 5)) / 100).toDouble()
            ps.sg_50 = (round(100 * (weight * dv["chs_50"]!! * 1440 / 500)) / 100).toDouble()
            ps.sg_10 = (round(100 * (weight * dv["chs_10"]!! * 1440 / 100)) / 100).toDouble()
            ps.kcl_amp_10 = (round(100 * (weight * dv["kcl_amp_10_"]!! / 2)) / 100).toDouble()
            ps.kcl_amp_5 = (round(100 * (weight * dv["kcl_amp_5_"]!! / 4)) / 100).toDouble()
            ps.naclhip = (round(100 * (weight * dv["naclhip_"]!! / 3)) / 100).toDouble()
            ps.sol_fisiológica = (round(100 * (weight * dv["sol_fisiológica_"]!! * 100 / 15.4)) / 100).toDouble()
            ps.fosfato_k = (round(100 * (weight * dv["fosfato_k_"]!! / 1.102)) / 100).toDouble()
            ps.glucca = (round(100 * (weight * dv["calcio_"]!! / 100)) / 100).toDouble()
            ps.magnesio = (round(100 * (weight * dv["magnesio_"]!! * 0.01)) / 100).toDouble()
            ps.mvi = if((1.7 * weight) > 5) { 5.0 } else { (round(100 * (1.7 * weight)) / 100).toDouble()}
            ps.oligoelementos = (round(100 * (dv["oligoelementos_"]!! * weight)) / 100).toDouble()
            ps.l_cisteína = if((proteins * weight * 100 / 2.5) > 100) { 100.0 } else { (round(100 * (proteins * weight * 100 / 2.5)) / 100).toDouble() }
            ps.carnitina = (round(100 * (dv["carnitina_"]!! * weight)) / 100).toDouble()
            if (patient.gestation >= 32) {
                ps.heparina = 0.0
            }else {
                ps.heparina = (round(100 * (dv["heparina_"]!! * ps.intralipid_20)) / 100).toDouble()
            }
            val sum = (round(100 * (ps.oligoelementos + ps.mvi + ps.magnesio + ps.glucca + ps.fosfato_k + ps.naclhip + ps.sol_fisiológica + ps.trophamine_10 + ps.trophamine_8 + ps.sg_50 + ps.sg_10 + ps.kcl_amp_10 + ps.kcl_amp_5 + ps.intralipid_20)) / 100)
            ps.abd = (round(100 * (ps.líquidos_iv_tot - sum)) / 100).toDouble()

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

            //viewModel?.insert(calculation, ps)
            viewModel?.insertSolution(ps)
        }

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
