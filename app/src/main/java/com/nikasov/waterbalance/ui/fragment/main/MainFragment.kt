package com.nikasov.waterbalance.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.nikasov.waterbalance.R
import com.nikasov.waterbalance.utils.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel : MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager()
    }

    private fun initPager() {

        val pagerAdapter = ViewPagerAdapter(
            viewModel.getOnboardingFragmentsList(),
            requireActivity().supportFragmentManager,
            lifecycle
        )

        mainPager.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(tabLayout, mainPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = resources.getString(R.string.home)
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_water_bw) }
                1 -> {
                    tab.text = resources.getString(R.string.statistic)
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_stat) }
                2 -> {
                    tab.text = resources.getString(R.string.settings)
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_settings)
                }
            }
        }.attach()
    }
}