package com.nikasov.waterbalance.ui.fragment.onboarding.first

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nikasov.waterbalance.R

class ProfileOnboardingFragment : Fragment(R.layout.fragment_profile_onboarding) {

    private val viewModel : ProfileOnboardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}