package com.nikasov.waterbalance.ui.fragment.onboarding.second

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nikasov.waterbalance.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsOnboardingFragment : Fragment(R.layout.fragment_settings_onboarding) {

    private val viewModel : SettingsOnboardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}