package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.repository

import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model.PixValidationPayload
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model.PixValidationResponse
import br.com.capivaras.android_pagbank.util.ApiInterface
import br.com.capivaras.android_pagbank.util.SharedPref

class PixValidationRepository(private val api: ApiInterface) {

    suspend fun validatePix(
        pixData: PixValidationPayload
    ): PixValidationResponse {
        val response =
            api.validate(pixData)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }
}
