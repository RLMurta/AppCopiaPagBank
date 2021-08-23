package br.com.capivaras.android_pagbank.features.home

import br.com.capivaras.android_pagbank.util.ApiInterface
import br.com.capivaras.android_pagbank.util.SendFcmTokenPayload

class HomeActivityRepository(
    private val endpoint: ApiInterface
) {
    suspend fun sendToken(tokenPhone: SendFcmTokenPayload) {
        endpoint.sendFcmToken(tokenPhone)
    }
}