package br.com.capivaras.android_pagbank.features.intro

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.capivaras.android_pagbank.databinding.IntroActivityBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: IntroActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IntroActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i("W", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            Log.i("Funcionou", task.result!!)
        })
    }
}
