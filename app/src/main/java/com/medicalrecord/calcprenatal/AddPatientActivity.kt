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
            var flag = 0
            val name =
                    if(addPatientNameEdit.text.isNotBlank()) {
                        addPatientNameEdit.text.toString()
                    } else {
                        flag++
                        addPatientNameEdit.error = "Requerido"
                    }
            val weight =
                    if(addPatientWeightEdit.text.isNotBlank()) {
                        addPatientWeightEdit.text.toString()
                    } else {
                        flag++
                        addPatientWeightEdit.error = "Requerido"
                    }
            if(flag == 0)
                startActivity<OtherInfoActivity>("name" to name, "weight" to weight)
        }

        addPatientCancelBtn.onClick { finish() }
    }
}
