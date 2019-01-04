package com.medicalrecord.utils

import android.content.Context
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

val Date.formatted: String
    get() {
        val formatter = SimpleDateFormat("MMM dd, yyyy")
        return formatter.format(this)
    }

val String.editable: Editable?
    get() {
        return Editable.Factory.getInstance().newEditable(this)
    }

fun Double.decimalsFormat(decimals: Int): String {
    return "%.${decimals}f".format(this)
}

fun View.showKeyboard() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}