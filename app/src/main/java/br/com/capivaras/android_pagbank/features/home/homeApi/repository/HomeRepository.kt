package br.com.capivaras.android_pagbank.features.home.homeApi.repository

import br.com.capivaras.android_pagbank.features.home.homeApi.model.HomeResponse
import br.com.capivaras.android_pagbank.util.ApiInterface
import br.com.capivaras.android_pagbank.util.SharedPref
import java.lang.Exception

class HomeRepository(private val api: ApiInterface) {

    suspend fun fetchHome(): HomeResponse {
        val response = api.home()
        if(response.isSuccessful){
            return response.body()!!
        } else{
            throw Exception()
        }
    }
}
