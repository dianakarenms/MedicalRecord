package com.medicalrecord.calcprenatal

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.medicalrecord.utils.PictureTools
import com.medicalrecord.utils.PictureTools.Companion.REQUEST_READ_EXTERNAL_STORAGE
import com.medicalrecord.utils.RealPathUtil
import com.medicalrecord.utils.editable
import kotlinx.android.synthetic.main.activity_doctor_data.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by diana.munoz on 10/1/18.
 */
class DoctorDataActivity: AppCompatActivity() {

    private val PREFS_FILENAME = "com.medicalrecord.calcprenatal"
    private val DOCTOR_NAME = "name"
    private val DOCTOR_LOGO = "logo"
    private val REQUEST_PICK_IMAGE = 1

    private var prefs: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var pathImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_data)
        setSupportActionBar(doctorDataToolbar)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        editor = prefs!!.edit()

        val doctorName = prefs!!.getString(DOCTOR_NAME, "")
        doctorDataNameEdit.text = doctorName.editable

        pathImage = prefs!!.getString(DOCTOR_LOGO, "")
        setDoctorBitmapFromUri(pathImage!!)

        doctorDataEditBtn.onClick { startActivity<EditValuesActivity>() }
        doctorDataPickLogoBtn.onClick {
            if (PictureTools.permissionReadMemory(this@DoctorDataActivity)) {
                chooseImageFromGallery()
            }
        }
        doctorDataSaveBtn.onClick {
            editor!!.putString(DOCTOR_NAME, doctorDataNameEdit.text.toString())
            pathImage?.let{ editor!!.putString(DOCTOR_LOGO, pathImage.toString()) }
            editor!!.apply()
            toast("Guardado exitoso")
            finish()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_PICK_IMAGE) {
            pathImage = getRealPathFromUri(data)
            setDoctorBitmapFromUri(pathImage!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults[1] == 0) {
            when (requestCode) {
                REQUEST_READ_EXTERNAL_STORAGE -> chooseImageFromGallery()
            }
        }
    }

    private fun setDoctorBitmapFromUri(pathImage: String) {
        if (pathImage.isNotEmpty()) {
            val bitmap = PictureTools.decodeSampledBitmapFromUri(pathImage, 200, 200)
            doctorDataImg.setImageBitmap(bitmap)
        }
    }

    private fun chooseImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Elige una imagen"), REQUEST_PICK_IMAGE)
    }

    private fun getRealPathFromUri(data: Intent?) : String {
        var path = ""
        data?.data!!.let { data ->
            path = when {
                Build.VERSION.SDK_INT < 11 -> RealPathUtil.getRealPathFromURI_BelowAPI11(this, data)
                Build.VERSION.SDK_INT < 19 -> RealPathUtil.getRealPathFromURI_API11to18(this, data)!!
                else -> RealPathUtil.getRealPathFromURI_API19(this, data)
            }
        }
        return path
    }
}
