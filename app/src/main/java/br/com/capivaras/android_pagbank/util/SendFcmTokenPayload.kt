package br.com.capivaras.android_pagbank.util

import com.google.gson.annotations.SerializedName

data class SendFcmTokenPayload (
    @SerializedName("fcm_token")
    val fcmToken: String
)