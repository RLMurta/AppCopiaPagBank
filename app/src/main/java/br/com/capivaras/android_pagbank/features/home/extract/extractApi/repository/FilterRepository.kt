package br.com.capivaras.android_pagbank.features.home.extract.extractApi.repository

import br.com.capivaras.android_pagbank.util.SharedPref

class FilterRepository(private val sharedPref: SharedPref) {

    fun setFilterPosition(positionFilter: Int) {
        sharedPref.saveFilter(positionFilter)
    }

    fun getFilterPosition(): Int {
        return sharedPref.pullFilter()
    }
}
