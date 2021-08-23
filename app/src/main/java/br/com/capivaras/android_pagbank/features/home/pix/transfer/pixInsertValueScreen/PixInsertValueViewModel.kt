package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixInsertValueScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.capivaras.android_pagbank.features.home.homeApi.repository.HomeRepository
import br.com.capivaras.android_pagbank.util.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigDecimal

class PixInsertValueViewModel(private val repository: HomeRepository): ViewModel() {
    private val _validValue = MutableLiveData<String>()
    val validValue: LiveData<String> = _validValue
    private val _balance = MutableLiveData<Double>()
    val balance: LiveData<Double> = _balance
    private var _hideBalancePix = MutableLiveData<Boolean>().apply { postValue(false) }
    val hideBalancePix: LiveData<Boolean> = _hideBalancePix
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val _enableContinueButton = MutableLiveData<Boolean>()
    val enableContinueButton: LiveData<Boolean> = _enableContinueButton
    private val _goToConfirmationScreen = SingleLiveEvent<Unit>()
    val goToConfirmationScreen: LiveData<Unit> = _goToConfirmationScreen

    fun isValueValid(initialValue: String) {
        if(initialValue.isNullOrEmpty())
            return
        val treatedValue = moneyTextWatcherToDouble(initialValue)
        if (treatedValue > 0.0 && treatedValue <= balance.value!!.toDouble()) {
            _validValue.postValue(null)
            _enableContinueButton.postValue(true)
        } else {
            _validValue.postValue("Insira um valor válido")
            _enableContinueButton.postValue(false)
        }
    }

    fun moneyTextWatcherToDouble(value: String): Double{
        val cleanValue = value.replace("[^0-9]".toRegex(), "")
        val treatedValue = BigDecimal(cleanValue).setScale(2, BigDecimal.ROUND_FLOOR).divide(
            BigDecimal(100), BigDecimal.ROUND_FLOOR).toDouble()
        return treatedValue
    }

    fun buttonHideClicked() {
        _hideBalancePix.postValue(_hideBalancePix.value?.not())
    }

    fun buttonContinueClicked() {
        _goToConfirmationScreen.postValue(Unit)
    }

    fun sendBalance() {
        viewModelScope.launch {
            try {
                val response = repository.fetchHome()
                _balance.postValue(response.balance.currentValue)
            } catch (e: Exception) {
                _error.postValue("Ops, ocorreu um erro, tente de novo mais tarde")
                _balance.postValue(0.0)
            }
        }
    }
}
