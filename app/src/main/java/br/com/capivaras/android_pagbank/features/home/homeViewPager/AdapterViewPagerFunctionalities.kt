package br.com.capivaras.android_pagbank.features.home.homeViewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.capivaras.android_pagbank.util.Constants.Companion.PAGE_POSITION

class AdapterViewPagerFunctionalities(fragmentbase: Fragment) : FragmentStateAdapter(fragmentbase) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return FragmentViewPagerFunctionalities().also {
            it.arguments = Bundle().apply {
                putInt(PAGE_POSITION, position)
            }
        }
    }
}
