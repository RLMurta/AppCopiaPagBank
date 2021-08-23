package br.com.capivaras.android_pagbank.features.home.extract.extractApi.model

import com.google.gson.annotations.SerializedName

data class ExtractResponse(
    val status: String,
    val time: String,
    val type: String,
    @SerializedName("type_description")
    val typeDescription: String,
    val value: Double,
    val date: String
)
