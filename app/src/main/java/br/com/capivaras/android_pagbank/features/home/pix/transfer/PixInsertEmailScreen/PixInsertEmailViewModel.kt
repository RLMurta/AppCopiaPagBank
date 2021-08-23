package br.com.capivaras.android_pagbank.features.home.pix.transfer.PixInsertEmailScreen

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.capivaras.android_pagbank.util.SingleLiveEvent

class PixInsertEmailViewModel() : ViewModel() {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val _enableContinueButton = MutableLiveData<Boolean>()
    val enableContinueButton: LiveData<Boolean> = _enableContinueButton
    private val _goToInsertDescription = SingleLiveEvent<Unit>()
    val goToInsertDescription: LiveData<Unit> = _goToInsertDescription

    fun checkIfEmailIsOk(email: String) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _error.postValue(null)
            _enableContinueButton.postValue(true)
        } else {
            _enableContinueButton.postValue(false)
        }
    }

    fun buttonClicked() {
        _goToInsertDescription.postValue(Unit)
    }
}
