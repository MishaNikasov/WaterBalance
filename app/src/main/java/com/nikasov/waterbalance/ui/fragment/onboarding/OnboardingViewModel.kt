package com.nikasov.waterbalance.ui.fragment.onboarding

import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nikasov.waterbalance.data.Prefs
import com.nikasov.waterbalance.ui.fragment.onboarding.first.ProfileOnboardingFragment
import com.nikasov.waterbalance.ui.fragment.onboarding.summary.SettingsOnboardingFragment
import com.nikasov.waterbalance.ui.fragment.onboarding.welcome.WelcomeOnboardingFragment

class OnboardingViewModel @ViewModelInject constructor(
    private val prefs: Prefs
) : ViewModel() {

    fun getOnboardingFragmentsList() : List<Fragment> {
        return arrayListOf(
            WelcomeOnboardingFragment(),
            ProfileOnboardingFragment(),
            SettingsOnboardingFragment()
        )
    }

    fun doneOnboarding() {
        prefs.saveIsOnboardingDone()
    }

}