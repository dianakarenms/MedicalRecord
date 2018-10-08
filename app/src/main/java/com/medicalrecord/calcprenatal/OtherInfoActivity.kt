package com.medicalrecord.calcprenatal

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.medicalrecord.calcprenatal.MainActivity.Companion.mDb
import com.medicalrecord.data.CalculationData
import com.medicalrecord.data.MedicalRecordDataBase
import com.medicalrecord.data.PatientData
import com.medicalrecord.utils.DbWorkerThread
import kotlinx.android.synthetic.main.activity_other_info.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by diana.munoz on 10/1/18.
 */
class OtherInfoActivity : AppCompatActivity() {

    private val mUiHandler = Handler()
    private lateinit var mDbWorkerThread: DbWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        setSupportActionBar(otherInfoToolbar)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
        mDb = MedicalRecordDataBase.getInstance(this)

        otherInfoSaveBtn.onClick {
            val patientData = PatientData()
            patientData.name = intent.getStringExtra("name")
            patientData.record = otherInfoExpEdit.text.toString()
            patientData.bed = otherInfoBedEdit.text.toString()
            patientData.gestation = otherInfoGestationEdit.text.toString().toLong()
            patientData.dx = otherInfoDxEdit.text.toString()
            patientData.date = Calendar.getInstance().time.formatted
            patientData.weight = intent.getStringExtra("weight").toDouble()

            insertPatientDataInDb(patientData)
            backToHome()
        }

        otherInfoCancelBtn.onClick { backToHome() }
    }

    override fun onDestroy() {
        MedicalRecordDataBase.destroyInstance()
        mDbWorkerThread.quit()
        super.onDestroy()
    }

    private fun backToHome() {
        startActivity(intentFor<MainActivity>().clearTop().clearTask())
        finish()
    }

    private fun insertPatientDataInDb(patientData: PatientData) {
        val task = Runnable {
            mDb?.patientDataDao()?.insert(patientData)
            /*val patientId = mDb?.patientDataDao()?.getLastInsertedId()
            mUiHandler.post {
                updatePatientCalculations(patientId)
            }*/
        }
        mDbWorkerThread.postTask(task)
    }

    private fun updatePatientCalculations(patientId: Long?) {
        val calculationData = CalculationData()
        calculationData.patientId = patientId!!
        calculationData.date = Calendar.getInstance().time.formatted
        calculationData.weight = intent.getStringExtra("weight").toDouble()

        val task = Runnable {
            mDb?.calculationDataDao()?.insert(calculationData)
        }
        mDbWorkerThread.postTask(task)
    }
}

private val Date.formatted: String
    get() {
        // Display a date in day, month, year format
        val formatter = SimpleDateFormat("MMM dd, yyyy")
        return formatter.format(this)
    }
