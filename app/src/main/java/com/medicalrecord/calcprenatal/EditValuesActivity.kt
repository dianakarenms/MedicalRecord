package com.medicalrecord.calcprenatal

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import com.medicalrecord.adapters.ValuesAdapter
import com.medicalrecord.data.RefValue
import com.medicalrecord.utils.Constants
import com.medicalrecord.utils.Constants.Companion.BASE_VALUES
import com.medicalrecord.utils.Constants.Companion.displayNames
import com.medicalrecord.utils.Constants.Companion.getHashMap
import com.medicalrecord.utils.Constants.Companion.saveHashMap
import com.medicalrecord.utils.editable
import com.medicalrecord.utils.hideKeyboard
import com.medicalrecord.utils.showKeyboard
import kotlinx.android.synthetic.main.activity_edit_values.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * Created by diana.munoz on 10/1/18.
 */
class EditValuesActivity: AppCompatActivity() {

    private var prefsValues = linkedMapOf<String, Double>()
    private var valuesList: MutableList<RefValue> = mutableListOf()
    private var adapter: ValuesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_values)

        adapter = ValuesAdapter {refValue, position ->
            showEditValueDialog(refValue, position)
        }
        editValuesRecycler.adapter = adapter
        editValuesRecycler.layoutManager = LinearLayoutManager(this@EditValuesActivity)

        editValuesCancelBtn.onClick { finish() }
        editValuesSaveBtn.onClick {
            saveHashMap(BASE_VALUES, prefsValues, this@EditValuesActivity)
            toast("Guardado exitoso")
            finish()
        }

        getHashMap(BASE_VALUES, this@EditValuesActivity).let {
            it ->
            prefsValues = it!!
            if (prefsValues != null) {
                for((key, value) in prefsValues) {
                    val refValue = RefValue(key, value, Constants.DOCTOR_REFERENCE)
                    valuesList.add(refValue)
                }
                adapter!!.setRefValues(valuesList)
            }
        }
    }

    private fun showEditValueDialog(refValue: RefValue, position: Int) {
        val view = layoutInflater.inflate(R.layout.dialog_value_field, null)

        var etComments: EditText = view.findViewById(R.id.etComments)
        etComments.text = refValue.value.toString().editable
        etComments.showKeyboard()

        var alertDialog = AlertDialog.Builder(this@EditValuesActivity).create()
        alertDialog.setCancelable(false)
        alertDialog.setMessage(displayNames[refValue.name]?.toUpperCase())
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialogInterface: DialogInterface, i: Int ->
            val newVal = etComments.text.toString()
            prefsValues[refValue.name] = newVal.toDouble()
            valuesList[position].value = newVal.toDouble()
            adapter?.notifyDataSetChanged()
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
