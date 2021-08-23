package br.com.capivaras.android_pagbank.features.home.homeViewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.ViewpagerFragmentFuncionalitsBinding
import br.com.capivaras.android_pagbank.util.Constants.Companion.PAGE_POSITION

class FragmentViewPagerFunctionalities : Fragment(),
    AdapterRecyclerviewFunctionalities.SelectItemsFunctionalities {
    private var _binding: ViewpagerFragmentFuncionalitsBinding? = null
    private val binding: ViewpagerFragmentFuncionalitsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = ViewpagerFragmentFuncionalitsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = AdapterRecyclerviewFunctionalities(
            getFunctionalitiesByViewPagerPosition(requireArguments().getInt(PAGE_POSITION)),
            this@FragmentViewPagerFunctionalities, requireArguments().getInt(PAGE_POSITION)
        )
    }

    fun getFunctionalitiesByViewPagerPosition(positionViewPager: Int): List<AdapterRecyclerviewFunctionalities.ItensList> {
        return when (positionViewPager) {
            0 -> {
                getMainFunctionalities()
            }
            1 -> {
                getProductsInvestmentsFunctionalities()
            }
            else -> {
                getGeneralServicesFunctionalities()
            }
        }
    }

    private fun getMainFunctionalities(): List<AdapterRecyclerviewFunctionalities.ItensList> {
        return listOf(
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.transfers),
                R.drawable.ic_baseline_transfer
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.refills),
                R.drawable.ic_baseline_phone_android_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.cards),
                R.drawable.ic_baseline_credit_card_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.add_money),
                R.drawable.ic_baseline_monetization_on_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.pay_the_bills),
                R.drawable.ic_baseline_receipt_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.pix_qrcode),
                R.drawable.ic_baseline_api_24
            )
        )
    }

    private fun getProductsInvestmentsFunctionalities(): List<AdapterRecyclerviewFunctionalities.ItensList> {
        return listOf(
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.apply_my_money),
                R.drawable.ic_baseline_show_chart_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.learn_to_Invest),
                R.drawable.ic_baseline_menu_book_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.my_Investments),
                R.drawable.ic_baseline_trending_up_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.insurance),
                R.drawable.ic_baseline_security_24
            ),
        )
    }

    private fun getGeneralServicesFunctionalities(): List<AdapterRecyclerviewFunctionalities.ItensList> {
        return listOf(
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.posts_Shell),
                R.drawable.ic_baseline_local_parking_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.where_to_withdraw_money),
                R.drawable.ic_baseline_monetization_on_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.offer_Radar),
                R.drawable.ic_baseline_stars_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.refer_and_earn),
                R.drawable.ic_baseline_card_giftcard_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.shopping),
                R.drawable.ic_baseline_shopping_cart_24
            ),
            AdapterRecyclerviewFunctionalities.ItensList(
                getString(R.string.pay_with_QR_Code),
                R.drawable.ic_baseline_qr_code_24
            )
        )
    }

    override fun onFunctionalityClicked(positionRecyclerView: Int, positionViewPager: Int) {
        if (positionRecyclerView == PIX_BUTTON_POSITION && positionViewPager == FIRST_RECYCLER_VIEW_PAGE) {
            findNavController().navigate(R.id.action_homeFragment_to_pix_activity)
        } else {
            Toast.makeText(requireContext(), "Ainda não implementado", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val PIX_BUTTON_POSITION = 5
        const val FIRST_RECYCLER_VIEW_PAGE = 0
    }
}

