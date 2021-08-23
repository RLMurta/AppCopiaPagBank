package br.com.capivaras.android_pagbank.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.capivaras.android_pagbank.util.SendFcmTokenPayload
import kotlinx.coroutines.launch

class HomeActivityViewModel(
    private val homeActivityRepository: HomeActivityRepository
) : ViewModel() {

    fun getToken(tokenPhone: SendFcmTokenPayload) {
        viewModelScope.launch {
            homeActivityRepository.sendToken(tokenPhone)
        }
    }
}