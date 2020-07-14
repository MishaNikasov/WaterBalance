package com.nikasov.waterbalance.ui.fragment.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nikasov.waterbalance.R
import com.nikasov.waterbalance.utils.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_onboarding.*

@AndroidEntryPoint
class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val viewModel : OnboardingViewModel by viewModels()

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

        boardingPager.apply {
            adapter = pagerAdapter
        }
    }
}