package br.com.capivaras.android_pagbank.features.home.pix.pixOnBoarding

import androidx.lifecycle.ViewModel
import br.com.capivaras.android_pagbank.util.SharedPref

class PixOnBoardingViewModel(private val sharedPref: SharedPref): ViewModel() {

    fun onViewCreated() {
        if(sharedPref.IsFirstPixLaunch()){
            sharedPref.setIsFirstPixLaunchToFalse()
        }
    }
}
