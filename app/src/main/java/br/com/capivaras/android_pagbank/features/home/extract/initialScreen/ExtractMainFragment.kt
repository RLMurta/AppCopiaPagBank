package br.com.capivaras.android_pagbank.features.home.extract.initialScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.ExtractMainFragmentBinding
import br.com.capivaras.android_pagbank.features.home.extract.filter.FilterActivity
import br.com.capivaras.android_pagbank.features.home.extract.initialScreen.ExtractViewModel.Companion.DEFAULT_SELECTED_POSITION
import br.com.capivaras.android_pagbank.features.loading.LoadingDialog
import br.com.capivaras.android_pagbank.util.Constants.Companion.DAYS_SELECT
import br.com.capivaras.android_pagbank.util.Constants.Companion.POSITION
import br.com.capivaras.android_pagbank.util.Constants.Companion.REQUEST_CODE
import br.com.capivaras.android_pagbank.util.Constants.Companion.RESULT_CODE
import br.com.capivaras.android_pagbank.util.ViewModelFactory


class ExtractMainFragment : Fragment() {
    private var _binding: ExtractMainFragmentBinding? = null
    private val binding: ExtractMainFragmentBinding get() = _binding!!
    private lateinit var extractViewModel: ExtractViewModel
    private val loadingDialog = LoadingDialog()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ExtractMainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instantiateVariables()

        subscribeObserver()

        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.ChipAll -> {
                    extractViewModel.onSelectedAll()
                }
                R.id.ChipEntrance -> {
                    extractViewModel.onSelectedEntrance()
                }
                R.id.ChipExit -> {
                    extractViewModel.onSelectedExit()
                }
            }
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bt_filter -> {
                    startActivityForResult(
                        Intent(requireActivity(), FilterActivity::class.java),
                        REQUEST_CODE
                    )
                    true
                }
                else -> false
            }
        }
    }

    private fun instantiateVariables() {
        extractViewModel = ViewModelProvider(
            this, ViewModelFactory(requireContext())
        ).get(ExtractViewModel::class.java)
    }

    private fun subscribeObserver() {
        extractViewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                loadingDialog.startLoadingDialog(requireActivity())
            } else {
                loadingDialog.dismissDialog()
            }
        })

        extractViewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        extractViewModel.extractList.observe(viewLifecycleOwner, {
            binding.extractsListRecyclerView.adapter = ExtractsAdapter(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE == requestCode && resultCode == RESULT_CODE) {
            val valueFilter = data!!.getStringExtra(DAYS_SELECT)
            Toast.makeText(requireContext(), valueFilter, Toast.LENGTH_LONG).show()
            extractViewModel.onDateSelect(data.getIntExtra(POSITION, DEFAULT_SELECTED_POSITION))
        }
    }
}
