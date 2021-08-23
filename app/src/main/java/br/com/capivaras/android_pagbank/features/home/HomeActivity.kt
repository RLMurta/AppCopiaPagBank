package br.com.capivaras.android_pagbank.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.HomeActivityBinding
import br.com.capivaras.android_pagbank.util.SendFcmTokenPayload
import br.com.capivaras.android_pagbank.util.ViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private lateinit var viewModel: HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        binding = HomeActivityBinding.inflate(layoutInflater)

        viewModel =
            ViewModelProvider(this, ViewModelFactory(this)).get(HomeActivityViewModel::class.java)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.conteinerHomeFragment) as NavHostFragment

        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.homeBottomNavigation)

        bottomNavigationView.setupWithNavController(navController)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            viewModel.getToken(SendFcmTokenPayload(task.result!!))
        })
    }
}
