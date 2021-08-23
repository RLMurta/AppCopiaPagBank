package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixConfirmationApi.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class PixConfirmationResponse(
    val user: User,
    val pix: String,
    val description: String,
    val organization: String,
    @SerializedName("pix_value")
    val pixValue: Double,
    val date: String
) {
    data class User(
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("first_name")
        val firstName: String
    )
}
