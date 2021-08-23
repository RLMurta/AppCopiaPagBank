package br.com.capivaras.android_pagbank.features.intro.login.loginApi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.capivaras.android_pagbank.features.intro.login.loginApi.repository.EmailNotFoundException
import br.com.capivaras.android_pagbank.features.intro.login.loginApi.repository.InvalidPasswordOrEmailException
import br.com.capivaras.android_pagbank.features.intro.login.loginApi.repository.LoginRepository
import br.com.capivaras.android_pagbank.features.loading.LoadingDialog
import br.com.capivaras.android_pagbank.util.SharedPref
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository, private val sharedPref: SharedPref) :
    ViewModel() {

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> = _emailError
    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = _passwordError
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val _success = MutableLiveData<Unit>()
    val success: LiveData<Unit> = _success
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _switchIsEnabled = MutableLiveData<Boolean>()
    val switchIsEnabled: LiveData<Boolean> = _switchIsEnabled
    private var email: String? = null
    private var password: String? = null

    fun onLoginClicked() {
        if (email.isNullOrBlank()) {
            _emailError.postValue("Ei, digite seu E-mail")
        } else if (password.isNullOrBlank()) {
            _passwordError.postValue("Ei, digite sua senha")
        } else {
            _emailError.postValue(null)
            _passwordError.postValue(null)
            doLogin(email!!, password!!)
        }
    }

    private fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val loginResponse = repository.login(email, password)
                sharedPref.setToken(loginResponse.tokenAuthentication)
                if(switchIsEnabled.value == true){
                    saveAccountOnSharedPref(email, password)
                }
                _success.postValue(Unit)
            } catch (e: EmailNotFoundException) {
                _emailError.postValue("Ops, email invalido")
            } catch (e: InvalidPasswordOrEmailException) {
                _passwordError.postValue("Ops, senha invalida")
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue("Ops, ocorreu um erro, tente de novo mais tarde")
            }
            _loading.postValue(false)
        }
    }

    fun onEmailChanged(email: String?) {
        this.email = email
        _emailError.postValue(null)
    }

    fun onPasswordChanged(password: String?) {
        this.password = password
        _passwordError.postValue(null)
    }

    fun getSwitchState(){
        _switchIsEnabled.postValue(sharedPref.getSaveAccountIsEnabled())
    }

    fun setSwitchState(switchIsEnabled: Boolean){
        sharedPref.setSaveAccountIsEnabled(switchIsEnabled)
        _switchIsEnabled.postValue(switchIsEnabled)
        if (!switchIsEnabled) {
            saveAccountOnSharedPref("", "")
        }
    }

    fun getAccountOnSharedPref(): Pair<String, String>{
        return sharedPref.getAccountDetails()
    }

    private fun saveAccountOnSharedPref(email: String, password: String){
        sharedPref.saveAccountDetails(email, password)
    }

    fun isAccountOnSharedPrefEmpty(): Boolean {
        return getAccountOnSharedPref() == Pair("", "")
    }
}
