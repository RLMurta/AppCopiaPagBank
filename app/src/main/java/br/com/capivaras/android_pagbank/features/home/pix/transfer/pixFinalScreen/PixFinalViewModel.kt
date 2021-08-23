package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixFinalScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixConfirmationApi.model.PixConfirmationResponse
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixConfirmationApi.repository.PixConfirmationRepository
import kotlinx.coroutines.launch

class PixFinalViewModel(private val repository: PixConfirmationRepository): ViewModel() {
    private val _pixData = MutableLiveData<PixConfirmationResponse>()
    val pixData: LiveData<PixConfirmationResponse> = _pixData
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun confirmPixAtributes(token: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val response = repository.confirmatePix(token)
                _pixData.postValue(response)
            } catch (e: Exception) {
                _error.postValue("Um erro ocorreu, tente novamente mais tarde")
            }
            _loading.postValue(false)
        }
    }
}
