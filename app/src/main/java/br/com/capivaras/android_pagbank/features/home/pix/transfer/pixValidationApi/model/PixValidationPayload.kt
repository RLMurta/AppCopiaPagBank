package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model

import java.io.Serializable
import java.util.*

data class PixValidationPayload(
    var email: String = "",
    var type: String = "email",
    var description: String = "",
    var value: Double = 0.0,
    var date: String = ""
): Serializable
