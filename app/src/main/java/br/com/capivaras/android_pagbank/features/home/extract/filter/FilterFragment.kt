package br.com.capivaras.android_pagbank.features.home.extract.filter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.FilterFragmentBinding
import br.com.capivaras.android_pagbank.util.Constants.Companion.DAYS_SELECT
import br.com.capivaras.android_pagbank.util.Constants.Companion.POSITION
import br.com.capivaras.android_pagbank.util.Constants.Companion.RESULT_CODE
import br.com.capivaras.android_pagbank.util.ViewModelFactory

class FilterFragment : Fragment(), AdapterRecyclerviewFilter.SelectItemsFilterListener {

    private var _binding: FilterFragmentBinding? = null
    private val binding: FilterFragmentBinding get() = _binding!!
    private lateinit var filterViewModel: FilterViewModel

    private val listdays: List<String> by lazy {
        listOf(
            getString(R.string.last_3_days), getString(R.string.last_7_days),
            getString(R.string.last_30_days), getString(R.string.last_60_days),
            getString(R.string.last_120_days)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FilterFragmentBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instantiateVariablesFilter()

        filterViewModel.selectedFilterPosition.observe(viewLifecycleOwner, {
            binding.Recycler.adapter = AdapterRecyclerviewFilter(listdays, this@FilterFragment, it)
        })

        filterViewModel.closeScreenWithResult.observe(viewLifecycleOwner, {
            requireActivity().setResult(RESULT_CODE,
                Intent().apply {
                    putExtra(DAYS_SELECT, listdays[it])
                    putExtra(POSITION, it)
                })
            requireActivity().finish()
        })

        binding.buttonFilter.setOnClickListener {
            filterViewModel.buttonFilterClicked()
        }

        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onPositionItemFilterSelected(position: Int) {
        filterViewModel.selectedFilterPosition(position)
    }

    private fun instantiateVariablesFilter() {
        filterViewModel = ViewModelProvider(
            this, ViewModelFactory(requireContext())
        ).get(FilterViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
