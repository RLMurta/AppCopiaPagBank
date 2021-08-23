package br.com.capivaras.android_pagbank.features.intro.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.SplashFragmentBinding
import br.com.capivaras.android_pagbank.util.ViewModelFactory

class SplashFragment : Fragment() {

    private var _binding : SplashFragmentBinding? = null
    private val binding: SplashFragmentBinding get() = _binding!!
    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(SplashViewModel::class.java)
        subscribeObservers()
        Handler().postDelayed(Runnable {
                viewModel.onViewCreated()
            }, 2000)

    }

    private fun subscribeObservers(){
        viewModel.goToOnBoarding.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
        })
        viewModel.goToLogin.observe(viewLifecycleOwner,{
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment32)
        })
    }
}
