package br.com.capivaras.android_pagbank.features.intro.login.loginApi.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val user: User,
    @SerializedName("token")
    val tokenAuthentication: String,
){
    data class User (val firstName: String, val lastName : String)
}
