package com.dianakarenms.medicalrecord.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dianakarenms.medicalrecord.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        toolbarMainDoctorSettingsBtn.setOnClickListener {
            startActivity<DoctorDataActivity>()
        }
    }
}
