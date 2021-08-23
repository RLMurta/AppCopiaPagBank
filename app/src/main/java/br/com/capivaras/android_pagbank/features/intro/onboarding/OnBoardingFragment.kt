package br.com.capivaras.android_pagbank.features.intro.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.OnBoardingFragmentBinding

class OnBoardingFragment : Fragment() {

    private var _binding: OnBoardingFragmentBinding? = null
    private val binding: OnBoardingFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OnBoardingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonComecar.setOnClickListener {
            goToLogin()
        }

        binding.buttonLogin.setOnClickListener {
            goToLogin()
        }
    }

    private fun goToLogin() {
        view?.findNavController()?.navigate(R.id.action_onBoardingFragment_to_loginFragment)
    }

}
