package com.medicalrecord.calcprenatal

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.medicalrecord.data.MedicalRecordDataBase
import com.medicalrecord.utils.DbWorkerThread
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    companion object {
        var mDb: MedicalRecordDataBase? = null
    }

    private val mUiHandler = Handler()
    private lateinit var mDbWorkerThread: DbWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)

        mDb = MedicalRecordDataBase.getInstance(this)
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        toolbarMainDoctorSettingsBtn.onClick {
            startActivity<DoctorDataActivity>()
        }

        mainAddPatientBtn.onClick {
            startActivity<AddPatientActivity>()
        }

        toolbarMainTestDbData.onClick {
            fetchPatientDataFromDb()
        }

        /*mainPatientsRecycler.layoutManager = LinearLayout(this@MainActivity,)
        mainPatientsRecycler.adapter = PatientsAdapter() {
            toast("${it.title} Clicked")
        }*/
    }

    private fun fetchPatientDataFromDb() {
        val task = Runnable {
            val patientData =
                    mDb?.patientDataDao()?.getAll()
            mUiHandler.post {
                if (patientData == null || patientData?.size == 0) {
                    toast("No data in cache..!!")
                } else {
                    for(patient in patientData) {
                        val task2 = Runnable {
                            val calculationData =
                                    mDb?.calculationDataDao()?.getCalculationsPerPatient(patient.id!!)
                            mUiHandler.post {
                                toast("${patient.name} " +
                                        "${calculationData?.get(0)?.weight}kg," +
                                        " ${calculationData?.get(0)?.date}")
                            }
                        }
                        mDbWorkerThread.postTask(task2)
                    }
                }
            }
        }
        mDbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        MedicalRecordDataBase.destroyInstance()
        mDbWorkerThread.quit()
        super.onDestroy()
    }
}
