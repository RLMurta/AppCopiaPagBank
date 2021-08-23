package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixInsertDescriptionScreen

import androidx.fragment.app.Fragment
import br.com.capivaras.android_pagbank.databinding.PixInsertDescriptionFragmentBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model.PixValidationPayload

class PixInsertDescriptionFragment : Fragment() {
    private var _binding: PixInsertDescriptionFragmentBinding? = null
    private val binding: PixInsertDescriptionFragmentBinding get() = _binding!!
    val args: PixInsertDescriptionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PixInsertDescriptionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons(view)
    }

    private fun setButtons(view: View) {
        val pix: PixValidationPayload = args.receivePix

        binding.buttonPixDescription.setOnClickListener {
            pix.description = binding.fieldInsertDescription.text.toString()
            val action =
                PixInsertDescriptionFragmentDirections.actionPixInsertDescriptionFragment2ToPixInsertValueFragment(
                    pix
                )
            view.findNavController().navigate(action)
        }

        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}
