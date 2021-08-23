package br.com.capivaras.android_pagbank.features.intro.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.capivaras.android_pagbank.util.SharedPref

class SplashViewModel(private val sharedPref: SharedPref) : ViewModel(){

    val goToOnBoarding = MutableLiveData<Unit>()
    val goToLogin = MutableLiveData<Unit>()

    fun onViewCreated() {
        if (sharedPref.isFirstLaunch()) {
            goToOnBoarding.postValue(Unit)
            sharedPref.setIsFirstLaunchToFalse()
        } else {
            goToLogin.postValue(Unit)
        }
    }
}
