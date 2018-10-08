package com.medicalrecord.calcprenatal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_aditional_instr.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * Created by diana.munoz on 10/1/18.
 */
class AditionalInstrActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aditional_instr)
        setSupportActionBar(aditionalInstrToolbar)

        aditionalInstrNextBtn.onClick {
            toast("En construcción")
        }
    }
}
