package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixConfirmationScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model.PixValidationPayload
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model.PixValidationResponse
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.repository.PixValidationRepository
import kotlinx.coroutines.launch

class PixConfirmationViewModel(private val repository: PixValidationRepository): ViewModel() {
    private val _pixData = MutableLiveData<PixValidationResponse>()
    val pixData: LiveData<PixValidationResponse> = _pixData
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun sendPixAtributes(pix: PixValidationPayload) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val response = repository.validatePix(pix)
                _pixData.postValue(response)
            } catch (e: Exception) {
                _error.postValue("Um erro ocorreu, tente novamente mais tarde")
            }
            _loading.postValue(false)
        }
    }
}
