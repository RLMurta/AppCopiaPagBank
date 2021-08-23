package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixConfirmationApi.repository

import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixConfirmationApi.model.PixConfirmationResponse
import br.com.capivaras.android_pagbank.util.ApiInterface
import br.com.capivaras.android_pagbank.util.SharedPref

class PixConfirmationRepository(private val api: ApiInterface){

    suspend fun confirmatePix(
        pixToken: String
    ): PixConfirmationResponse {
        val response = api.confirm(pixToken)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }
}
