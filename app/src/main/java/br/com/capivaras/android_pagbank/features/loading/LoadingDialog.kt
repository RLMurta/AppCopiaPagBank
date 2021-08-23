package br.com.capivaras.android_pagbank.features.loading

import android.app.Activity
import android.app.AlertDialog
import br.com.capivaras.android_pagbank.R

class LoadingDialog() {

    private lateinit var dialog : AlertDialog

    fun startLoadingDialog(activity: Activity){
        val builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}
