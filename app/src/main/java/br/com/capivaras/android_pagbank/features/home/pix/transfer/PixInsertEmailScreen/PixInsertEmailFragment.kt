package br.com.capivaras.android_pagbank.features.home.pix.transfer.PixInsertEmailScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import br.com.capivaras.android_pagbank.databinding.TransferPixEmailBinding
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model.PixValidationPayload
import br.com.capivaras.android_pagbank.util.ViewModelFactory

class PixInsertEmailFragment : Fragment() {

    private var _binding: TransferPixEmailBinding? = null
    private val binding: TransferPixEmailBinding get() = _binding!!
    private lateinit var viewModel: PixInsertEmailViewModel
    private lateinit var pix: PixValidationPayload

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TransferPixEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(requireContext())
        ).get(PixInsertEmailViewModel::class.java)

        binding.fieldEmailPix.addTextChangedListener {
            viewModel.checkIfEmailIsOk(it.toString())
        }

        viewModel.error.observe(viewLifecycleOwner, { error ->
            binding.boxPixEmail.error = error
        })

        viewModel.enableContinueButton.observe(viewLifecycleOwner, { buttonState ->
            binding.buttonContinuePayPixEmail.isEnabled = buttonState
        })

        viewModel.goToInsertDescription.observe(viewLifecycleOwner, {
            val action =
                PixInsertEmailFragmentDirections.actionPixEmailFragmentToPixInsertDescriptionFragment2(
                    pix
                )
            view.findNavController().navigate(action)
        })

        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.buttonContinuePayPixEmail.setOnClickListener {
            onButtonContinueClicked()
        }
    }

    private fun onButtonContinueClicked() {
        pix = PixValidationPayload()
        pix.email = binding.fieldEmailPix.text.toString()
        viewModel.buttonClicked()
    }
}
