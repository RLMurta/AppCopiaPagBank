package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixInitialScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.TransferPixBinding

class PixInitialFragment : Fragment() {

    private var _binding: TransferPixBinding? = null
    private val binding: TransferPixBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TransferPixBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()
        setButtons(view)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    view?.findNavController()?.navigate(R.id.action_pixInitialFragment_to_pixFragment)
                }
            }
        )
    }

    private fun setButtons(view: View) {
        binding.button1.pixButtonConstraintLayout.setOnClickListener {
            view.findNavController().navigate(R.id.action_pixInitialFragment_to_pixEmailFragment)
        }

        binding.button2.pixButtonConstraintLayout.setNotImplementationClickListener()

        binding.button3.pixButtonConstraintLayout.setNotImplementationClickListener()

        binding.button4.pixButtonConstraintLayout.setNotImplementationClickListener()

        binding.button5.pixButtonConstraintLayout.setNotImplementationClickListener()

        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setButtons() {
        binding.button2.pixTextBox.text = getString(R.string.cellphone)
        binding.button3.pixTextBox.text = getString(R.string.personal_id_or_company_id)
        binding.button4.pixTextBox.text = getString(R.string.random_key)
        binding.button5.pixTextBox.text = getString(R.string.agency_and_account)
    }

    fun View.setNotImplementationClickListener(){
        setOnClickListener {
            Toast.makeText(requireContext(), "Ainda não implementado", Toast.LENGTH_SHORT).show()
        }
    }
}
