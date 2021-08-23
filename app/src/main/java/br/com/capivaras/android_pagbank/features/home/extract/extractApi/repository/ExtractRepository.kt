package br.com.capivaras.android_pagbank.features.home.extract.extractApi.repository

import br.com.capivaras.android_pagbank.features.home.extract.extractApi.model.ExtractResponse
import br.com.capivaras.android_pagbank.util.ApiInterface
import br.com.capivaras.android_pagbank.util.SharedPref

class ExtractRepository(private val api: ApiInterface) {

    suspend fun fetchExtract(startDate: String, endDate: String): List<ExtractResponse> {
        val response = api.extract(startDate, endDate)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }

}
