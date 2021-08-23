package br.com.capivaras.android_pagbank.features.home.pix.pixOnBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.PixOnBoardingFragmentBinding
import br.com.capivaras.android_pagbank.util.ViewModelFactory

class PixOnBoardingFragment : Fragment() {

    private var _binding: PixOnBoardingFragmentBinding? = null
    private val binding: PixOnBoardingFragmentBinding get() = _binding!!
    private lateinit var viewModel: PixOnBoardingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PixOnBoardingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(PixOnBoardingViewModel::class.java)
        viewModel.onViewCreated()

        binding.closeFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_pixOnBoardingFragment_to_pixFragment)
        }

        binding.continueFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_pixOnBoardingFragment_to_pixFragment)
        }
    }
}
