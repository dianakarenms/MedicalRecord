package com.medicalrecord.calcprenatal.activities

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import com.medicalrecord.calcprenatal.R
import kotlinx.android.synthetic.main.activity_doctor_data.*
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import java.io.FileNotFoundException
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager




/**
 * Created by diana.munoz on 10/1/18.
 */
class DoctorDataActivity: AppCompatActivity() {

    private val PREFS_FILENAME = "com.medicalrecord.calcprenatal"
    private val DOCTOR_NAME = "name"
    private val DOCTOR_LOGO = "logo"
    private val PICK_IMAGE = 1

    private var prefs: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var imgUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_data)
        setSupportActionBar(doctorDataToolbar)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        editor = prefs!!.edit()

        val doctorName = prefs!!.getString(DOCTOR_NAME, "")
        doctorDataNameEdit.text = Editable.Factory.getInstance().newEditable(doctorName)

        val doctorLogo = Uri.parse(prefs!!.getString(DOCTOR_LOGO, ""))
        //doctorLogo?.let { bitmapFromUri(doctorLogo) }


        doctorDataPickLogoBtn.onClick {
            if (ActivityCompat.checkSelfPermission(this@DoctorDataActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@DoctorDataActivity, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_IMAGE)
            } else {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Elige una imagen"), PICK_IMAGE)
            }
        }

        doctorDataBackBtn.onClick { finish() }

        doctorDataSaveBtn.onClick {
            editor!!.putString(DOCTOR_NAME, doctorDataNameEdit.text.toString())
            imgUri?.let{ editor!!.putString(DOCTOR_LOGO, imgUri.toString()) }
            editor!!.apply()
            toast("Guardado exitoso")
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imgUri = data?.data!!
            imgUri.let {
                try {
                    bitmapFromUri(imgUri!!)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun bitmapFromUri(uri:Uri) {
        val imageStream = contentResolver.openInputStream(uri)
        val selectedImage = BitmapFactory.decodeStream(imageStream);
        doctorDataImg.imageBitmap = selectedImage
    }
}
