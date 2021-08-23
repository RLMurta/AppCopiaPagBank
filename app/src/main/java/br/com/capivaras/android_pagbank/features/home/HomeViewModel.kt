package br.com.capivaras.android_pagbank.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.capivaras.android_pagbank.features.home.homeApi.model.HomeResponse
import br.com.capivaras.android_pagbank.features.home.homeApi.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository): ViewModel() {
    private val _balance = MutableLiveData<HomeResponse.Balance>()
    val balance: LiveData<HomeResponse.Balance> = _balance
    private val _benefits = MutableLiveData<List<HomeResponse.Benefit>>()
    val benefits: LiveData<List<HomeResponse.Benefit>> = _benefits
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private var _hideBalanceData = MutableLiveData<Boolean>().apply { postValue(false) }
    val hideBalanceData: LiveData<Boolean> = _hideBalanceData

    fun sendHomeAtributes() {
        viewModelScope.launch {
            try {
                val response = repository.fetchHome()
                _balance.postValue(response.balance)
                _benefits.postValue(response.benefits)
            } catch (e: Exception) {
                _error.postValue("Ops, ocorreu um erro, tente de novo mais tarde")
            }
        }
    }

    fun buttonEyeClicked(){
        _hideBalanceData.postValue(_hideBalanceData.value?.not())
    }
}
