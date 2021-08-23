package br.com.capivaras.android_pagbank.features.intro.login.loginApi.repository

import br.com.capivaras.android_pagbank.features.intro.login.loginApi.model.LoginPayload
import br.com.capivaras.android_pagbank.features.intro.login.loginApi.model.LoginResponse
import br.com.capivaras.android_pagbank.util.ApiInterface

class LoginRepository(private val api: ApiInterface) {

    suspend fun login(email: String, password: String) : LoginResponse {
        val response = api.login(LoginPayload(email, password))
        if (response.isSuccessful){
            return response.body()!!
        } else if(response.code() == INVALID_PASSWORD_OR_EMAIL){
            throw InvalidPasswordOrEmailException()
        } else if(response.code() == EMAIL_NOT_FOUND){
            throw EmailNotFoundException()
        } else{
            throw Exception()
        }
    }

    companion object{
        private const val INVALID_PASSWORD_OR_EMAIL = 401
        private const val EMAIL_NOT_FOUND = 404
    }

}
