package br.com.capivaras.android_pagbank.util

import br.com.capivaras.android_pagbank.features.home.extract.extractApi.model.ExtractResponse
import br.com.capivaras.android_pagbank.features.home.homeApi.model.HomeResponse
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixConfirmationApi.model.PixConfirmationResponse
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model.PixValidationPayload
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model.PixValidationResponse
import br.com.capivaras.android_pagbank.features.intro.login.loginApi.model.LoginPayload
import br.com.capivaras.android_pagbank.features.intro.login.loginApi.model.LoginResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    @POST("login")
    suspend fun login(@Body loginPayload: LoginPayload): Response<LoginResponse>

    @GET("extract")
    suspend fun extract(
        @Query("start") startDate: String,
        @Query("end") endDate: String
    ): Response<List<ExtractResponse>>

    @GET("home")
    suspend fun home(): Response<HomeResponse>

    @POST("pix/confirm")
    suspend fun confirm(
        @Header("pix_token") pixToken: String
    ): Response<PixConfirmationResponse>

    @POST("pix/validation")
    suspend fun validate(
        @Body pixPayload: PixValidationPayload,
    ): Response<PixValidationResponse>

    @POST("home/sendfcm")
    suspend fun sendFcmToken(
        @Body tokenFirebase: SendFcmTokenPayload
    ): Response<Void>

    companion object {

        fun create(sessionManager: SharedPref): ApiInterface {
            return Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(providerClient(sessionManager))
                .build()
                .create(ApiInterface::class.java)
        }

        fun providerClient(sessionManager: SharedPref): OkHttpClient {
            return OkHttpClient
                .Builder()
                .apply {
                    addInterceptor(AuthInterceptor(sessionManager))
                    addInterceptor(HttpLoggingInterceptor()
                        .apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                }
                .build()
        }
    }
}
