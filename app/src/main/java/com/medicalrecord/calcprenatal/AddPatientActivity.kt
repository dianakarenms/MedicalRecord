package com.medicalrecord.calcprenatal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_patient.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * Created by diana.munoz on 10/1/18.
 */
class AddPatientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)
        setSupportActionBar(addPatientToolbar)

        addPatientNextBtn.onClick {
            val name = addPatientNameEdit.text.toString()
            val weight = addPatientWeightEdit.text.toString()
            startActivity<OtherInfoActivity>("name" to name, "weight" to weight)
        }

        addPatientCancelBtn.onClick { finish() }
    }
}
