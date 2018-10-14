package com.medicalrecord.calcprenatal

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import com.medicalrecord.adapters.ValuesAdapter
import com.medicalrecord.utils.Constants
import com.medicalrecord.utils.editable
import kotlinx.android.synthetic.main.activity_edit_values.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * Created by diana.munoz on 10/1/18.
 */
class EditValuesActivity: AppCompatActivity() {

    private val BASE_VALUES = "base_values"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_values)

        editValuesCancelBtn.onClick { finish() }
        editValuesSaveBtn.onClick {  }

        if( Constants.getHashMap(BASE_VALUES, this@EditValuesActivity) == null ) {
            Constants.saveHashMap(BASE_VALUES, Constants.baseValues, this@EditValuesActivity)
        }

        Constants.getHashMap(BASE_VALUES, this@EditValuesActivity).let {
            prefsValues ->
            var valuesList: MutableList<Value> = mutableListOf()
            if (prefsValues != null) {
                for((key, value) in prefsValues) {
                    val refValue = Value(key, value.toString())
                    valuesList.add(refValue)
                    editValuesRecycler.layoutManager = LinearLayoutManager(this@EditValuesActivity)
                    editValuesRecycler.adapter = ValuesAdapter(valuesList) {
                        showEditValueDialog(it.value)
                    }
                }
            }
        }
    }

    private fun showEditValueDialog(text: String) {
        val view = layoutInflater.inflate(R.layout.dialog_value_field, null);
        var alertDialog = AlertDialog.Builder(this@EditValuesActivity).create();
        alertDialog.setCancelable(true)
        alertDialog.setMessage("Editar valor")

        var etComments: EditText = view.findViewById(R.id.etComments)
        etComments.text = text.editable
        etComments.requestFocus()

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialogInterface: DialogInterface, i: Int ->
            toast("ok")
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialogInterface: DialogInterface, i: Int ->
            alertDialog.dismiss()
        }

        alertDialog.setView(view)
        alertDialog.show()
    }
}

class Value(
        var name: String,
        var value: String
)
