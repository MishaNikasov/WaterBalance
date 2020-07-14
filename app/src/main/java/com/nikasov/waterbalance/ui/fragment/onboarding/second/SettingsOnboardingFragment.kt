package com.nikasov.waterbalance.ui.fragment.onboarding.second

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nikasov.waterbalance.R
import com.nikasov.waterbalance.ui.fragment.onboarding.first.ProfileOnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings_onboarding.*

@AndroidEntryPoint
class SettingsOnboardingFragment : Fragment(R.layout.fragment_settings_onboarding) {

    private val viewModel : SettingsOnboardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        end.setOnClickListener {
            viewModel.doneOnboarding()
            findNavController().navigate(R.id.action_onboardingFragment_to_mainFragment)
        }
    }
}