package com.medicalrecord.utils

import android.text.Editable
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