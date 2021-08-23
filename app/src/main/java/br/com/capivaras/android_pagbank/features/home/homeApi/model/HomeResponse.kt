package br.com.capivaras.android_pagbank.features.home.homeApi.model

import com.google.gson.annotations.SerializedName

class HomeResponse(
    val balance: Balance,
    val benefits: List<Benefit>
) {
    data class Balance(
        @SerializedName("current_value")
        val currentValue: Double,
        val receivables: Int
    )

    data class Benefit(
        @SerializedName("indicator_color")
        val indicatorColor: String,
        val image: String,
        val title: String,
        val message: String,
        @SerializedName("text_link")
        val textLink: String
    )
}
