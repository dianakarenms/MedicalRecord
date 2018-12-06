package com.medicalrecord.utils

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.medicalrecord.data.viewmodels.CalculationViewModel

class CustomViewModelFactory(private val application: Application, private val patientId: Long) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalculationViewModel(application, patientId) as T
    }

}