package br.com.capivaras.android_pagbank.features.home.extract.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.capivaras.android_pagbank.features.home.extract.extractApi.repository.FilterRepository

class FilterViewModel (private val repository: FilterRepository) : ViewModel(){

    private val _selectedFilterPosition = MutableLiveData<Int>()
    val selectedFilterPosition: LiveData<Int> = _selectedFilterPosition
    private val _closeScreenWithResult = MutableLiveData<Int>()
    val closeScreenWithResult: LiveData<Int> = _closeScreenWithResult
    private var positionSelect: Int = 1


    init {
        getFilterPosition()
    }

    fun getFilterPosition() {
        _selectedFilterPosition.postValue(repository.getFilterPosition())
    }

    fun buttonFilterClicked() {
        repository.setFilterPosition(positionSelect)
        _closeScreenWithResult.postValue(positionSelect)

    }

    fun selectedFilterPosition(position: Int) {
        this.positionSelect = position
    }
}
