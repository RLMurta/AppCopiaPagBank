package br.com.capivaras.android_pagbank.features.home.extract.filter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.capivaras.android_pagbank.databinding.FilterActivityBinding

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: FilterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
