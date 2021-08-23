package br.com.capivaras.android_pagbank.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor (private val sharedPreferences: SharedPref): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(sharedPreferences.getToken()!!.isNotEmpty()){
            return chain.proceed(chain.request().newBuilder().addHeader("token",
                sharedPreferences.getToken()!!).build())
        }else{
            return chain.proceed(chain.request().newBuilder().build())
        }
    }
}
