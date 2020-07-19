package com.nikasov.waterbalance.ui.fragment.onboarding.summary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nikasov.waterbalance.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings_onboarding.*

@AndroidEntryPoint
class SettingsOnboardingFragment : Fragment(R.layout.fragment_settings_onboarding) {

    private val viewModel : SettingsOnboardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.goal.observe(viewLifecycleOwner, Observer {
            val goalStr = "$it ml"
            goal.text = goalStr
        })
    }
}