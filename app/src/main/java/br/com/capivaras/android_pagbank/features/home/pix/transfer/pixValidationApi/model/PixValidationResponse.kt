package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class PixValidationResponse(
    val user: User,
    val pix: String,
    val description: String,
    val organization: String,
    @SerializedName("pix_value")
    val pixValue: Double,
    @SerializedName("pix_token")
    val pixToken: String,
    val date: String
) {
    data class User(
        @SerializedName("last_name")
        val lastName:String,
        @SerializedName("first_name")
        val firstName: String
    )
}
