package br.com.capivaras.android_pagbank.features.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.HomeFragmentBinding
import br.com.capivaras.android_pagbank.features.home.homeApi.recyclerView.BenefitsAdapter
import br.com.capivaras.android_pagbank.features.home.homeViewPager.AdapterViewPagerFunctionalities
import br.com.capivaras.android_pagbank.util.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import java.text.NumberFormat

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding: HomeFragmentBinding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private val onPixBroadcastReceived = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.sendHomeAtributes()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(onPixBroadcastReceived, IntentFilter("ON_PIX_RECEIVED"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instantiateVariables()
        TabViewPageLayout()

        viewModel.sendHomeAtributes()

        viewModel.balance.observe(requireActivity(), { balance ->
            binding.balanceText.text = NumberFormat.getCurrencyInstance().format(balance.currentValue)
        })

        viewModel.benefits.observe(requireActivity(), { benefits ->
            binding.RecyclerViewBenefits.adapter = BenefitsAdapter(benefits)
        })

        binding.buttonBalance.setOnClickListener{
            viewModel.buttonEyeClicked()
        }

        viewModel.hideBalanceData.observe(requireActivity(), { hideBalance ->
            if (hideBalance) {
                binding.buttonBalance.setImageResource(R.drawable.balance_visibility_off)
                binding.balanceText.inputType = TYPE_CLASS_TEXT
                binding.salesTextSmall.setText("0.00")
            } else {
                binding.buttonBalance.setImageResource(R.drawable.view_balance)
                binding.balanceText.inputType = TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT
                binding.salesTextSmall.setText("••••")
            }
        })

        binding.seeDetailsBalance.setNotImplementationClickListener()

        binding.arrowAlmostThere.setNotImplementationClickListener()
    }

    private fun instantiateVariables() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(requireContext())
        ).get(HomeViewModel::class.java)
    }

    private fun TabViewPageLayout() {
        binding.pagerFunctionality.adapter = AdapterViewPagerFunctionalities(this)
        tabLayoutInstance()
    }

    private fun tabLayoutInstance() {
        TabLayoutMediator(
            binding.tabLayoutFunctionality,
            binding.pagerFunctionality
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.main)
                }
                1 -> {
                    tab.text = getString(R.string.products_and_investiments)
                }
                2 -> {
                    tab.text = getString(R.string.service)
                }
            }
        }.attach()
    }

    fun View.setNotImplementationClickListener(){
        setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.not_yet_implemented), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(onPixBroadcastReceived)
    }
}
