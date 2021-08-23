package br.com.capivaras.android_pagbank.features.home.pix.transfer.pixFinalScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.PixFinalFragmentBinding
import br.com.capivaras.android_pagbank.features.loading.LoadingDialog
import br.com.capivaras.android_pagbank.util.ViewModelFactory
import java.text.NumberFormat
import java.time.format.DateTimeFormatter

class PixFinalFragment : Fragment() {
    private var _binding: PixFinalFragmentBinding? = null
    private val binding: PixFinalFragmentBinding get() = _binding!!
    private lateinit var viewModel: PixFinalViewModel
    private val args: PixFinalFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PixFinalFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pixToken: String = args.receivePixToken
        val loadingDialog = LoadingDialog()
        setButtons()

        viewModel = ViewModelProvider(
            this, ViewModelFactory(requireContext())
        ).get(PixFinalViewModel::class.java)

        viewModel.confirmPixAtributes(pixToken)

        viewModel.pixData.observe(requireActivity(), { pixData ->
            binding.transferValueHolder.text = NumberFormat.getCurrencyInstance().format(pixData.pixValue)
            val completeName = "${pixData.user.firstName} ${pixData.user.lastName}"
            binding.transferToNameHolder.text = completeName
            binding.transferToIdHolder.text = pixData.pix
            binding.transferStatusDateAndTimeHolder.text = pixData.date
        })

        viewModel.error.observe(requireActivity(), {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    view?.findNavController()?.navigate(R.id.action_pixFinalFragment_to_pixInitialFragment)
                }
            }
        )

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            if(isLoading){
                loadingDialog.startLoadingDialog(requireActivity())
            } else{
                loadingDialog.dismissDialog()
            }
        })
    }

    private fun setButtons() {
        binding.button1.buttonConstraintLayout.setNotImplementationClickListener()

        binding.button2.buttonIcon.setImageResource(R.drawable.pag_blue_ic_baseline_person_outline_24)
        binding.button2.buttonText.text = getString(R.string.do_another_transfer)
        binding.button2.buttonConstraintLayout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_pixFinalFragment_to_pixInitialFragment)
        }

        binding.button3.buttonIcon.setImageResource(R.drawable.pag_blued_ic_baseline_home_24)
        binding.button3.buttonText.text = getString(R.string.go_to_the_start)
        binding.button3.buttonConstraintLayout.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun View.setNotImplementationClickListener(){
        setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.not_yet_implemented), Toast.LENGTH_SHORT).show()
        }
    }
}
