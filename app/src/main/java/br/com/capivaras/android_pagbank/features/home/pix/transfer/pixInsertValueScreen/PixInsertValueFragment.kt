package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixInsertValueScreen

import android.os.Bundle
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.PixInsertValueFragmentBinding
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model.PixValidationPayload
import br.com.capivaras.android_pagbank.util.ViewModelFactory
import java.text.NumberFormat
import android.text.Editable
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import java.util.*

class PixInsertValueFragment : Fragment() {

    private var _binding: PixInsertValueFragmentBinding? = null
    private val binding: PixInsertValueFragmentBinding get() = _binding!!
    private lateinit var viewModel: PixInsertValueViewModel
    private val args: PixInsertValueFragmentArgs by navArgs()
    private lateinit var pix: PixValidationPayload


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PixInsertValueFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(requireContext())
        ).get(PixInsertValueViewModel::class.java)

        viewModel.sendBalance()

        binding.fieldInsertValue.addTextChangedListener {
            viewModel.isValueValid(it.toString())
        }

        binding.fieldInsertValue.addTextChangedListener(MoneyTextWatcher(binding.fieldInsertValue))

        viewModel.hideBalancePix.observe(requireActivity(), { hideBalance ->
            if (hideBalance) {
                binding.buttonHideBalance.setText(getString(R.string.hide))
                binding.BalanceTextView.inputType = InputType.TYPE_CLASS_TEXT
            } else {
                binding.buttonHideBalance.setText(getString(R.string.show_balance))
                binding.BalanceTextView.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            }
        })

        viewModel.balance.observe(requireActivity(), { balance ->
            binding.BalanceTextView.text = NumberFormat.getCurrencyInstance().format(balance)
        })

        viewModel.error.observe(requireActivity(), {
            Toast.makeText(
                requireContext(),
                getString(R.string.an_error_has_ocurred_try_again_later),
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.enableContinueButton.observe(viewLifecycleOwner, { buttonState ->
            binding.buttonPixValue.isEnabled = buttonState
        })

        viewModel.validValue.observe(requireActivity(), { error ->
            binding.insertValue.error = error
        })

        viewModel.goToConfirmationScreen.observe(requireActivity(), {
            val action =
                PixInsertValueFragmentDirections.actionPixInsertValueFragmentToPixConfirmationFragment(
                    pix
                )
            findNavController().navigate(action)
        })

        binding.buttonPixValue.setOnClickListener {
            pix = args.receivePix
            pix.value =
                viewModel.moneyTextWatcherToDouble(binding.fieldInsertValue.text.toString())
            viewModel.buttonContinueClicked()
        }
    }

    private fun setButtons() {
        binding.buttonHideBalance.setOnClickListener {
            viewModel.buttonHideClicked()
        }

        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}
