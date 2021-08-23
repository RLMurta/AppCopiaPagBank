package br.com.capivaras.android_pagbank.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import br.com.capivaras.android_pagbank.features.intro.login.loginApi.model.LoginPayload

class SharedPref private constructor() {

    fun setIsFirstPixLaunchToFalse() {
        editor!!.putBoolean(IS_FIRST_PIX_LAUNCH, false)
        editor!!.commit()
    }

    fun IsFirstPixLaunch(): Boolean {
        return sharedPreferences!!.getBoolean(IS_FIRST_PIX_LAUNCH, true)
    }

    fun setIsFirstLaunchToFalse() {
        editor!!.putBoolean(IS_FIRST_LAUNCH, false)
        editor!!.commit()
    }

    fun isFirstLaunch(): Boolean {
        return sharedPreferences!!.getBoolean(IS_FIRST_LAUNCH, true)
    }

    fun setToken(token: String) {
        editor?.putString(TOKEN, token)
        editor?.apply()
    }

    fun getToken(): String? {
        return sharedPreferences!!.getString(TOKEN, "")
    }

    fun saveFilter(filterPosition: Int) {
        editor?.putInt(VALUE_FILTER_POSITION, filterPosition)
        editor?.apply()
    }

    fun setSaveAccountIsEnabled(isEnabled: Boolean) {
        editor?.putBoolean(SAVE_ACCOUNT_IS_ENABLED, isEnabled)
        editor?.apply()
    }

    fun getSaveAccountIsEnabled(): Boolean {
        return sharedPreferences!!.getBoolean(SAVE_ACCOUNT_IS_ENABLED, false)
    }

    fun saveAccountDetails(email: String, password: String) {
        editor?.putString(EMAIL, email)
        editor?.putString(PASSWORD, password)
        editor?.apply()
    }

    fun getAccountDetails(): Pair<String, String> {
        return Pair(
            sharedPreferences!!.getString(EMAIL, "")!!, sharedPreferences!!.getString(
                PASSWORD, ""
            )!!
        )
    }

    fun pullFilter(): Int = sharedPreferences!!.getInt(VALUE_FILTER_POSITION, 1)

    companion object {
        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null
        private val IS_FIRST_LAUNCH = "is_first_launch"
        private val IS_FIRST_PIX_LAUNCH = "is_pix_first_launch"
        private val VALUE_FILTER_POSITION = "position_filter"
        private val TOKEN = "token"
        private val SAVE_ACCOUNT_IS_ENABLED = "account"
        private val EMAIL = "email"
        private val PASSWORD = "password"

        @Synchronized
        fun getInstance(context: Context): SharedPref {

            if (sharedPreferences == null) {
                sharedPreferences =
                    context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                editor = sharedPreferences!!.edit()
            }
            return SharedPref()
        }
    }
}
