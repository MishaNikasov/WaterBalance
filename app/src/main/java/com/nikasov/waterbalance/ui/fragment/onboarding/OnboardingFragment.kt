package com.nikasov.waterbalance.ui.fragment.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
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

        setBtnClick(false)

        val listener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        btnNext.text = resources.getString(R.string.next)
                        setBtnClick(false)
                    }
                    1 -> {
                        btnNext.text = resources.getString(R.string.finish)
                        setBtnClick(true)
                    }
                }
            }
        }

        boardingPager.apply {
            adapter = pagerAdapter
            registerOnPageChangeCallback(listener)
        }

        dotsIndicator.setViewPager2(boardingPager)
    }

    private fun setBtnClick(isNext: Boolean) {
        if (!isNext)
            btnNext.setOnClickListener {
                boardingPager.currentItem = boardingPager.currentItem + 1
            } else {
                btnNext.setOnClickListener {
                    viewModel.doneOnboarding()
                    findNavController().navigate(R.id.action_onboardingFragment_to_mainFragment)
            }
        }
    }
}