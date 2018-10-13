package com.medicalrecord.utils

import java.text.SimpleDateFormat
import java.util.*

private val Date.formatted: String
    get() {
        val formatter = SimpleDateFormat("MMM dd, yyyy")
        return formatter.format(this)
    }