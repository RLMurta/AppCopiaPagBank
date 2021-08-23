package br.com.capivaras.android_pagbank.features.home.pix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.PixActivityBinding
import br.com.capivaras.android_pagbank.util.SharedPref

class PixActivity : AppCompatActivity() {
    private lateinit var binding: PixActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PixActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.pixNavHostFragment) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.pix_nav_graph)
        val navController = navHostFragment.navController

        val sharedPref = SharedPref.getInstance(this)
        val destination = if (sharedPref.IsFirstPixLaunch()) {
            R.id.pixOnBoardingFragment
        } else {
            R.id.pixFragment
        }

        navGraph.startDestination = destination
        navController.graph = navGraph
    }
}
