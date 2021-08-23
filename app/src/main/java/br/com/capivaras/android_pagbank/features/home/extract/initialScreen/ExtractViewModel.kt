package br.com.capivaras.android_pagbank.features.home.extract.initialScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.capivaras.android_pagbank.features.home.extract.extractApi.model.ExtractResponse
import br.com.capivaras.android_pagbank.features.home.extract.extractApi.repository.ExtractRepository
import br.com.capivaras.android_pagbank.util.Constants
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ExtractViewModel(private val repository: ExtractRepository) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val _extractList = MutableLiveData<List<TransactionItemAdapter>>()
    val extractList: LiveData<List<TransactionItemAdapter>> = _extractList
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val listDays: List<Int> = listOf(3, 7, 30, 60, 120)
    private var response: List<ExtractResponse>? = null

    init {
        onDateSelect(DEFAULT_SELECTED_POSITION)
    }

    fun onDateSelect(position: Int) {
        val endDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        startDate.add(Calendar.DAY_OF_MONTH, - listDays[position])
        getExtract(dateFormat.format(startDate.time), dateFormat.format(endDate.time))
    }

    private fun getExtract(startDate: String, endDate: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                response = repository.fetchExtract(
                    startDate,
                    endDate
                )
                onSelectedAll()
            } catch (e: Exception) {
                _error.postValue(Constants.ERRO_API)
            }
            _loading.postValue(false)
        }
    }

    fun onSelectedAll() {
        mapItemsAdapter(response.orEmpty())
    }

    private fun mapItemsAdapter(listExtract: List<ExtractResponse>) {
        val listItemAdapter: MutableList<TransactionItemAdapter> = mutableListOf()
        for (position in listExtract.indices) {
            if (position == 0 || listExtract[position].date != listExtract[position - 1].date) {
                listItemAdapter.add(TransactionHeader(listExtract[position].date))
            }
            listItemAdapter.add(TransactionBody(listExtract[position]))
        }
        _extractList.postValue(listItemAdapter)
    }

    fun onSelectedEntrance() {
        mapItemsAdapter(response.orEmpty().filter { it.type == Constants.PAYMENT })
    }

    fun onSelectedExit() {
        mapItemsAdapter(response.orEmpty().filter { it.type == Constants.EXPENSE })
    }

    open class TransactionItemAdapter

    data class TransactionHeader(val date: String) : TransactionItemAdapter()

    data class TransactionBody(val extractInfo: ExtractResponse) : TransactionItemAdapter()

    companion object{
        const val DEFAULT_SELECTED_POSITION = 1
    }
}
