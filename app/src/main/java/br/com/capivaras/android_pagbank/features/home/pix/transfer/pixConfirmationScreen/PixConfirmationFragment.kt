package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixConfirmationScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.PixConfirmationFragmentBinding
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.model.PixValidationPayload
import br.com.capivaras.android_pagbank.features.loading.LoadingDialog
import br.com.capivaras.android_pagbank.util.ViewModelFactory
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class PixConfirmationFragment : Fragment() {

    private var _binding: PixConfirmationFragmentBinding? = null
    private val binding: PixConfirmationFragmentBinding get() = _binding!!
    private lateinit var viewModel: PixConfirmationViewModel
    private val args: PixConfirmationFragmentArgs by navArgs()
    private lateinit var pixToken: String
    private lateinit var daySelect: Calendar
    private val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    private lateinit var dateOfTransfer: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = PixConfirmationFragmentBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pix: PixValidationPayload = args.receivePix
        val loadingDialog = LoadingDialog()

        viewModel = ViewModelProvider(
            this, ViewModelFactory(requireContext())
        ).get(PixConfirmationViewModel::class.java)

        daySelect = Calendar.getInstance()
        dateOfTransfer = dateFormat.format(daySelect.time)
        binding.pixCalendar.text = dateOfTransfer
        pix.date = dateOfTransfer

        setCalendar(pix)
        setButtons(view)
        callingApi(pix)

        viewModel.pixData.observe(requireActivity(), { pixData ->
            val completeName = "${pixData.user.firstName} ${pixData.user.lastName}"
            binding.textViewPixPrice.text = NumberFormat.getCurrencyInstance().format(pixData.pixValue)
            binding.textViewPixPriceTransaction.text = NumberFormat.getCurrencyInstance().format(pixData.pixValue)
            binding.nameDestination.text = completeName
            binding.insertTextViewEmail.text = pixData.pix
            binding.insertTextViewInstitution.text = pixData.organization
            binding.insertTextViewDescription.text = pixData.description
            pixToken = pixData.pixToken
        })

        viewModel.error.observe(requireActivity(), {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                loadingDialog.startLoadingDialog(requireActivity())
            } else {
                loadingDialog.dismissDialog()
            }
        })
    }

    private fun setCalendar(
        pix: PixValidationPayload
    ) {
        binding.pixCalendar.setOnClickListener {
            MaterialDatePicker.Builder.datePicker().setTitleText(getString(R.string.select_a_day))
                .setCalendarConstraints(
                    CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())
                        .build()
                ).build().apply {
                    addOnPositiveButtonClickListener {
                        daySelect.time = Date(it)
                        dateOfTransfer = dateFormat.format(daySelect.time)
                        binding.pixCalendar.text = dateOfTransfer
                        pix.date = dateOfTransfer
                        callingApi(pix)
                    }
                }.show(childFragmentManager, "data")
        }
    }

    private fun callingApi(pix: PixValidationPayload) {
        viewModel.sendPixAtributes(pix)
    }

    private fun setButtons(view: View) {
        binding.buttonFilter.setOnClickListener {
            val action =
                PixConfirmationFragmentDirections.actionPixConfirmationFragmentToPixFinalFragment(
                    pixToken
                )
            view.findNavController().navigate(action)
        }

        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btPixCancel.setOnClickListener {
            requireActivity().finish()
        }
    }
}
